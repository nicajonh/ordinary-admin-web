/**
 * WebSecurityConfig 安全框架配置
 *
 * CreatedAt: 2020-06-02 23:07
 *
 * @author llh
 */
package com.llh.server.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.llh.server.common.constant.AuthExpInfo
import com.llh.server.common.util.JwtTokenUtil
import com.llh.server.service.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter(), WebMvcConfigurer {
    @Autowired
    @Qualifier("sysUserService")
    private lateinit var sysUserService: SysUserService

    @Autowired
    private lateinit var jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(this.sysUserService)
            ?.passwordEncoder(passwordEncoder());
    }

    /**
     * 全局CORS配置
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")// 允许跨域访问的路径
            .allowedOrigins("*")// 允许跨域访问的源
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")// 允许请求方法
            .maxAge(168000)// 预检间隔时间
            .allowedHeaders("*")// 允许头部设置
            .allowCredentials(true) // 是否发送cookie

    }

    override fun configure(http: HttpSecurity) {
        // 由于使用的是JWT，我们这里不需要csrf
        http.cors().disable()
            .csrf().disable()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.GET,
                "/",
                "/*.html",
                "/**/*.js",
                "/**/*.html",
                "/**/*.css",
                "/swagger/**",
                "/actuator/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/test/**", // 测试
                "/druid/**",
                "/favicon.ico"
            ).permitAll()
            .antMatchers("/auth/**").permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated()
        // 异常处理（登陆失败的处理）
        http.exceptionHandling()
            .authenticationEntryPoint(UnauthorizedEntryPoint())
        // 添加JWT filter
        http.addFilterBefore(
            jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

    }

    @Bean
    fun unauthorizedEntryPoint(): UnauthorizedEntryPoint {
        return UnauthorizedEntryPoint()
    }

}

// 登陆失败的处理
class UnauthorizedEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse?,
                          e: AuthenticationException?) {
        val mapper = ObjectMapper()
        val map = mutableMapOf<String, Any>().apply {
            this["code"] = AuthExpInfo.NOT_LOGIN.code
            this["msg"] = AuthExpInfo.NOT_LOGIN.msg
        }
        val json = mapper.writeValueAsString(map)
        response?.characterEncoding = "utf-8"
        response?.contentType = "application/json; charset=utf-8"
        response?.writer?.write(json)
    }
}

@Component
class JwtAuthenticationTokenFilter : OncePerRequestFilter() {
    @Value("\${jwt.header}")
    private lateinit var tokenHeader: String

    @Value("\${jwt.refreshToken}")
    private lateinit var refreshTokenHeader: String

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    @Qualifier("sysUserService")
    private lateinit var userService: SysUserService

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        // 放行所有OPTIONS请求
        if (request.method == "OPTIONS") {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader(this.tokenHeader)
        if (jwtTokenUtil.validateToken(authHeader)) {
            val username = jwtTokenUtil.extractUsername(authHeader)
            if (username != null) {
                val userDetails = userService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}

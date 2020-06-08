package com.llh.webserver.common.config;

import com.llh.webserver.common.config.security.JwtAuthenticationEntryPoint;
import com.llh.webserver.common.config.security.JwtAuthenticationTokenFilter;
import com.llh.webserver.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebSecurityConfig 安全框架配置
 * <p>
 * CreatedAt: 2020-04-18 17:03
 *
 * @author llh
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    @Qualifier("sysUserService")
    private SysUserService sysUserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 全局CORS配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 允许跨域访问的路径
            .allowedOrigins("*")// 允许跨域访问的源
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")// 允许请求方法
            .maxAge(168000)// 预检间隔时间
            .allowedHeaders("*")// 允许头部设置
            .allowCredentials(true); // 是否发送cookie
    }

    /**
     * 用户信息从数据库中获取
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.sysUserService)
            .passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 由于使用的是JWT，我们这里不需要csrf
        http.cors().disable()
            .csrf().disable()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            // 放行OPTIONS请求
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 允许对于网站静态资源的无授权访问
            .antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/**/*.js",
                "/**/*.html",
                "/**/*.css",
                "/swagger/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/druid/**",
                "/favicon.ico"
            ).permitAll()
            // 暂时开放认证模块下所有API
            .antMatchers("/auth/**").permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated();
        // 异常处理
        http.exceptionHandling()
            .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        // 添加JWT filter
        http.addFilterBefore(
            authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }

}

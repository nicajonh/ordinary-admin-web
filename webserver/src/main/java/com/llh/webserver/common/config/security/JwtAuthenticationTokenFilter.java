package com.llh.webserver.common.config.security;

import com.llh.webserver.service.SysUserService;
import com.llh.webserver.util.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthenticationTokenFilter token认证过滤器
 * 此过滤器需要配置在{@link UsernamePasswordAuthenticationFilter}过滤器之前
 * <p>
 * CreatedAt: 2020-05-23 09:37
 *
 * @author llh
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.refreshToken}")
    private String refreshTokenHeader;

    @Autowired
    @Qualifier("sysUserService")
    private SysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        // 放行所有OPTIONS请求
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader(this.tokenHeader);
        if (JwtTokenUtil.validateToken(authHeader)) {
            String username = JwtTokenUtil.getUsername(authHeader);
            UserDetails userDetails = sysUserService.loadUserByUsername(username);
            // 给UsernamePasswordAuthenticationFilter过滤器构造数据
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
            // 将登录信息放到 Security 上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }
}

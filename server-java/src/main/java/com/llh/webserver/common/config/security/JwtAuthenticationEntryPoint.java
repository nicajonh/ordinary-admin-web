package com.llh.webserver.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtAuthenticationEntryPoint
 * <p>
 * CreatedAt: 2020-05-23 09:33
 *
 * @author llh
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException e)
        throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", BasicExpEnum.AUTH_NOTLOGIN_ERROR.getMsg());
        map.put("code", BasicExpEnum.AUTH_NOTLOGIN_ERROR.getCode());
        String json = mapper.writeValueAsString(map);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}

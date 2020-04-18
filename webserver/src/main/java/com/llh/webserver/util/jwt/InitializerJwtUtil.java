package com.llh.webserver.util.jwt;

import org.springframework.stereotype.Component;

/**
 * InitializerJwtUtil
 * <p>
 * CreatedAt: 2020-04-18 22:00
 *
 * @author llh
 */
@Component
public class InitializerJwtUtil {
    private TokenSettings tokenSettings; // spring 会注入相关的类

    public InitializerJwtUtil(TokenSettings tokenSettings) {
        JwtTokenUtil.setTokenSettings(tokenSettings);
    }
}

package com.llh.webserver.util.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * TokenSettings
 * <p>
 * CreatedAt: 2020-04-18 21:58
 *
 * @author llh
 */
@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class TokenSettings {
    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private Duration refreshTokenExpireAppTime;
    private String issuer;
}

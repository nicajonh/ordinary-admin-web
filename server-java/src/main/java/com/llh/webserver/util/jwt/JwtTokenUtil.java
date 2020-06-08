package com.llh.webserver.util.jwt;

import com.llh.webserver.common.constant.JWTConstant;
import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * JwtTokenUtil
 * <p>
 * CreatedAt: 2020-04-18 22:00
 *
 * @author llh
 */
@Slf4j

public class JwtTokenUtil {
    private static String secretKey;
    private static Duration accessTokenExpireTime;
    private static Duration refreshTokenExpireTime;
    private static Duration refreshTokenExpireAppTime;
    private static String issuer;

    public static void setTokenSettings(TokenSettings tokenSettings) {
        secretKey = tokenSettings.getSecretKey();
        accessTokenExpireTime = tokenSettings.getAccessTokenExpireTime();
        refreshTokenExpireTime = tokenSettings.getRefreshTokenExpireTime();
        refreshTokenExpireAppTime = tokenSettings.getRefreshTokenExpireAppTime();
        issuer = tokenSettings.getIssuer();
    }


    /**
     * 生成 access_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息
     * @return access_token
     */
    public static String generateAccessToken(String subject, Map<String, Object> claims) {
        claims.put("typ", "access_token");
        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }


    /**
     * 生成 refresh_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息
     * @return refresh_token
     */
    public static String generateRefreshToken(String subject, Map<String, Object> claims) {
        claims.put("typ", "refresh_token");
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }


    /**
     * 生成 refresh_app_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息
     * @return refresh_app_token
     */
    public static String generateRefreshAppToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireAppTime.toMillis(), secretKey);
    }


    /**
     * 从令牌中获取数据
     *
     * @param token 令牌
     * @return {@link Claims}数据
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("Parse token error ,this token is {} ", token);
            claims = null;
        }
        return claims;
    }

    /**
     * 从token中获取用户id。
     * token解析错误时会抛出异常{@link BusinessException}
     *
     * @param token 令牌
     * @return 用户id。
     */
    public static String getUserId(String token) {
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("Failed to get userId from token , this token is {} \n", token, e);
            throw new BusinessException(BasicExpEnum.TOKEN_PARSE_EXCEPTION);
        }
        return userId;
    }

    /**
     * 从token中获取用户名。
     * token解析错误时会抛出异常{@link BusinessException}
     *
     * @param token 令牌
     * @return 用户名。
     */
    public static String getUsername(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(JWTConstant.JWT_USERNAME);
        } catch (Exception e) {
            log.error("Failed to get userName from token , this token is {} \n", token, e);
            throw new BusinessException(BasicExpEnum.TOKEN_PARSE_EXCEPTION);
        }
        return username;
    }


    /**
     * 验证token是否过期。默认返回true，即过期
     *
     * @param token 令牌
     * @return 是否过期。默认返回true，即过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.error("Unable to verify whether the token has expired , this token is {} \n", token, e);
            return true;
        }
    }

    /**
     * 校验令牌可用性。
     * 令牌内容不为空并且没有过期，则令牌是可用的。
     *
     * @param token 令牌
     * @return 令牌是否可用。
     */
    public static Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null != claimsFromToken && !isTokenExpired(token));
    }


    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新token
     * @param claims       刷新令牌时需要重新写入到令牌中的信息。没有则传null
     * @return 新的token或null
     */
    public static String refreshToken(String refreshToken, Map<String, Object> claims) {
        String refreshedToken;
        try {
            Claims parserClaims = getClaimsFromToken(refreshToken);
            if (null == claims) {
                claims = parserClaims;
            }
            refreshedToken = generateToken(
                parserClaims.getIssuer(), parserClaims.getSubject(), claims,
                accessTokenExpireTime.toMillis(), secretKey);
        } catch (Exception e) {
            refreshedToken = null;
            log.error("Error refreshing token , this token is {} \n", refreshToken, e);
        }
        return refreshedToken;
    }


    /**
     * 获取token的剩余过期时间
     *
     * @param token 令牌
     * @return long
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime() - nowMillis;
        } catch (Exception e) {
            log.error("Unable to get the remaining expiration time of the token , " +
                " this token is {} \n", token, e);
        }
        return result;
    }

    /**
     * 获取token的签发时间
     *
     * @param token 令牌
     * @return token的签发时间;默认值为0
     */
    public static long getIssuedTime(String token) {
        try {
            return getClaimsFromToken(token).getIssuedAt().getTime();
        } catch (Exception e) {
            log.error("Unable to get the issued  time of the token , " +
                " this token is {} \n", token, e);
        }
        return 0;
    }


    /**
     * 签发token
     *
     * @param issuer    签发人
     * @param subject   代表这个JWT的主体，即它的所有人。（用户id）
     * @param claims    存储在JWT里面的信息。如：用户的权限/角色信息
     * @param ttlMillis 有效时间(毫秒)
     * @param secret    用于加密的字符串
     * @return token
     */
    public static String generateToken(String issuer, String subject,
                                       Map<String, Object> claims, long ttlMillis,
                                       String secret) {
        // 设置加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 用于加密的字符串转换为字节
        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        // 设置信息
        if (null != claims) {
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        // 生成token
        return builder
            .signWith(signatureAlgorithm, signingKey)
            .compact();
    }
}

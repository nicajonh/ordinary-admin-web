package com.llh.server.common.util


import cn.hutool.core.util.StrUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.xml.bind.DatatypeConverter
import org.apache.logging.log4j.kotlin.Logging

/**
 * JwtTokenUtil JWT工具类
 *
 * CreatedAt: 2020-06-03 22:22
 *
 * @author llh
 */
@Component
class JwtTokenUtil : Logging {
    @Value("\${jwt.secretKey}")
    lateinit var secretKey: String

    @Value("\${jwt.accessTokenExpireTime}")
    lateinit var accessTokenExpireTime: Duration

    @Value("\${jwt.refreshTokenExpireTime}")
    lateinit var refreshTokenExpireTime: Duration

    @Value("\${jwt.refreshTokenExpireAppTime}")
    lateinit var refreshTokenExpireAppTime: Duration

    @Value("\${jwt.issuer}")
    lateinit var issuer: String

    /**
     * 生成 refresh_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息
     * @return refresh_token
     */
    fun generateRefreshToken(subject: String, claims: Map<String, Any>): String {
        if (claims is MutableMap) {
            claims["typ"] = "refresh_token"
        }
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生成 access_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息
     * @return access_token
     */
    fun generateAccessToken(subject: String, claims: Map<String, Any>): String {
        if (claims is MutableMap) {
            claims["typ"] = "refresh_token"
        }
        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 从[token]令牌中获取数据
     */
    fun extractClaimsFrom(token: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(DatatypeConverter.parseAnySimpleType(secretKey))
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            logger.error("Parse token error ,this token is $token")
            return null
        }
    }

    /**
     * 从token中获取用户id。
     */
    fun extractUserId(token: String): String? {
        val claims = extractClaimsFrom(token)
        return claims?.subject
    }

    /**
     * 从token中获取用户名。
     */
    fun extractUsername(token: String): String? {
        val claims = extractClaimsFrom(token)
        return claims?.get(JWT_USERNAME) as String
    }

    /**
     * 验证[token]是否已经过期。默认返回true，即过期。
     */
    fun isTokenExpired(token: String): Boolean {
        val isExpired = extractClaimsFrom(token)
            ?.expiration?.before(Date())
        return isExpired ?: true
    }

    /**
     * 校验令牌可用性。
     * 令牌内容不为空并且没有过期，则令牌是可用的。
     * @return 令牌是否可用。
     */
    fun validateToken(token: String): Boolean {
        val claimsFromToken = extractClaimsFrom(token)
        return (null != claimsFromToken) && isTokenExpired(token)
    }

    /**
     * 获取token的签发时间.
     */
    fun extractIssuedTime(token: String): Long? {
        return extractClaimsFrom(token)
            ?.issuedAt?.time
    }

    /**
     *  获取token的剩余过期时间
     */
    fun extractRemainingTime(token: String): Long {
        val remaining = extractClaimsFrom(token)
            ?.expiration?.time
        return if (null == remaining) 0L
        else remaining - System.currentTimeMillis()

    }


    /**
     * 签发token
     *
     * @param issuer    签发人
     * @param subject   代表这个JWT的主体，即它的所有人。（用户id）
     * @param claims    存储在JWT里面的信息。如：用户的权限/角色信息
     * @param ttlMillis 有效时间(毫秒)
     * @param secret    用于加密的字符串
     * @return 加密生成的token
     */
    fun generateToken(issuer: String, subject: String?,
                      claims: Map<String, Any>?, ttlMillis: Long,
                      secret: String): String {
        val alg = SignatureAlgorithm.HS256
        val nowMillis = System.currentTimeMillis()
        // 用于加密的字符串转换为字节
        val signingKey = DatatypeConverter.parseAnySimpleType(secret)
        val builder = Jwts.builder()
        claims ?: builder.setClaims(claims)
        if (StrUtil.isNotBlank(subject)) {
            builder.setSubject(subject)
        }
        builder.setIssuedAt(Date())

        if (ttlMillis > 0) {
            val expMillis = ttlMillis + System.currentTimeMillis()
            builder.setExpiration(Date(expMillis))
        }
        return builder.signWith(alg, signingKey)
            .compact()
    }
}

const val JWT_ROLES_KEY = "roles"
const val JWT_PERMISSIONS_KEY = "permissions"
const val JWT_USERNAME = "username"
const val JWT_USER_ID = "user_id"


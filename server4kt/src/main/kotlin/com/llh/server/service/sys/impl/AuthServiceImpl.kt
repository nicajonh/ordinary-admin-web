package com.llh.server.service.sys.impl

import com.llh.server.common.constant.AuthExpInfo
import com.llh.server.common.exception.BasicException
import com.llh.server.common.util.JWT_ROLES_KEY
import com.llh.server.common.util.JWT_USERNAME
import com.llh.server.common.util.JwtTokenUtil
import com.llh.server.pojo.AccountVO
import com.llh.server.pojo.vo.AuthTokenVO
import com.llh.server.pojo.vo.LoginVO
import com.llh.server.service.sys.AuthService
import com.llh.server.service.sys.SysUserService
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * AuthServiceImpl
 *
 * CreatedAt: 2020-06-06 15:50
 *
 * @author llh
 */
@Service("authService")
class AuthServiceImpl : AuthService, Logging {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    @Qualifier("sysUserService")
    private lateinit var sysUserService: SysUserService

    override fun login(loginVO: LoginVO): AuthTokenVO {
        val details = sysUserService
            .loadUserByUsername(loginVO.username) as AccountVO
        if (!passwordEncoder.matches(loginVO.password, details.password)) {
            logger.info("$loginVO 密码不正确.")
            throw BasicException(AuthExpInfo.LOGIN_ERROR)
        }
        return createAccountTokenVO(details)
    }

    /**
     * 生成token信息类
     */
    private fun createAccountTokenVO(details: AccountVO): AuthTokenVO {
        val claims = genClaims(details)
        return AuthTokenVO(
            accessToken = genAccessToken(details.id, claims),
            refreshToken = genRefreshToken(details.id, claims),
            username = details.username
        )
    }

    /**
     * 设置基本信息
     */
    private fun genClaims(user: UserDetails): MutableMap<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims[JWT_USERNAME] = user.username
        claims[JWT_ROLES_KEY] = user.authorities
        return claims
    }

    /**
     * 生成访问token
     */
    private fun genAccessToken(userId: String, claims: Map<String, Any>): String {
        return jwtTokenUtil.generateAccessToken(userId, claims)
    }

    /**
     * 生成刷新token
     */
    private fun genRefreshToken(userId: String, claims: Map<String, Any>): String {
        return jwtTokenUtil.generateRefreshToken(userId, claims)
    }
}

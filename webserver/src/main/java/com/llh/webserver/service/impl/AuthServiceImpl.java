package com.llh.webserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.llh.webserver.common.constant.JWTConstant;
import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.auth.AuthTokenVO;
import com.llh.webserver.pojo.auth.LoginVO;
import com.llh.webserver.pojo.auth.RegisterOrUpdateVO;
import com.llh.webserver.service.AuthService;
import com.llh.webserver.util.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthServiceImpl
 * <p>
 * CreatedAt: 2020-04-18 18:09
 *
 * @author llh
 */
@Service("authService")
@Slf4j
public class AuthServiceImpl extends SysUserServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthTokenVO login(LoginVO loginVO) {
        SysUser details = (SysUser) loadUserByUsername(loginVO.getUsername());
        if (!passwordEncoder.matches(loginVO.getPassword(), details.getPassword())) {
            log.info("密码不正确 : {} ", loginVO);
            throw new BusinessException(BasicExpEnum.Auth_ERROR);
        }
        return createAccountTokenVO(details);
    }

    @Override
    @Transactional
    public RegisterOrUpdateVO register(RegisterOrUpdateVO registerVO) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(registerVO, user, getCopyOptions());
        user.setPassword(registerVO.getPassword());
        save(user);
        return registerVO;
    }

    @Override
    public boolean logout(AuthTokenVO tokenVO) {
        return false;
    }


    /**
     * 生成token信息类
     *
     * @param userDetails 用户信息
     * @return 帐户token信息类
     */
    private AuthTokenVO createAccountTokenVO(SysUser userDetails) {
        AuthTokenVO tokenVO = new AuthTokenVO();
        Map<String, Object> claims = genClaims(userDetails);
        tokenVO.setAccessToken(genAccessToken(userDetails.getId(), claims))
            .setRefreshToken(genRefreshToken(userDetails.getId(), claims))
            .setId(userDetails.getId())
            .setUsername(userDetails.getUsername());
        return tokenVO;
    }

    /**
     * 设置基本信息
     *
     * @param user 用户信息
     * @return 用于填充JWT中claims部分信息的Map
     */
    private Map<String, Object> genClaims(SysUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWTConstant.JWT_USERNAME, user.getUsername());
        claims.put(JWTConstant.JWT_ROLES_KEY, user.getAuthorities());
        claims.put(JWTConstant.JWT_USER_ID, user.getId());
        return claims;
    }

    /**
     * 生成访问token
     *
     * @param userId 用户id
     * @param claims JWT中claims部分信息
     * @return 访问token/接入令牌
     */
    private String genAccessToken(String userId, Map<String, Object> claims) {
        return JwtTokenUtil.generateAccessToken(userId, claims);
    }


    /**
     * 生成刷新token
     *
     * @param userId 用户id
     * @param claims JWT中claims部分信息
     * @return 刷新token
     */
    private String genRefreshToken(String userId, Map<String, Object> claims) {
        return JwtTokenUtil.generateRefreshToken(userId, claims);
    }
}

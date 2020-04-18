package com.llh.webserver.service;

import com.llh.webserver.pojo.auth.AuthTokenVO;
import com.llh.webserver.pojo.auth.LoginVO;
import com.llh.webserver.pojo.auth.RegisterOrUpdateVO;

/**
 * AuthService
 * <p>
 * CreatedAt: 2020-04-18 18:05
 *
 * @author llh
 */
public interface AuthService extends SysUserService {
    /**
     * 登录方法
     *
     * @param loginVO 登录信息
     * @return JWT信息类
     */
    AuthTokenVO login(LoginVO loginVO);

    /**
     * 注册新用户的方法
     *
     * @param registerVO 注册信息的VO类
     * @return 不知道返回个啥好。暂时先返回输入的吧。
     */
    RegisterOrUpdateVO register(RegisterOrUpdateVO registerVO);

    /**
     * 登出时对token的操作
     *
     * @param tokenVO tokenVO类
     * @return 退出成功与否
     */
    boolean logout(AuthTokenVO tokenVO);
}

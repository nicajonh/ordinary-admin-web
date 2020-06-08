package com.llh.webserver.controller;

import com.llh.webserver.model.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * BasicController
 * <p>
 * CreatedAt: 2020-04-18 17:45
 *
 * @author llh
 */
public abstract class BasicController {
    public static final String ACCESS_TOKEN = "authorization";
    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * 获取当前用户
     *
     * @return 当前登录的用户
     */
    public SysUser getCurrentUser() {
        return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

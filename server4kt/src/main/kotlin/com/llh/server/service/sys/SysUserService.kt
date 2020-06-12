package com.llh.server.service.sys

import com.llh.server.common.constant.AccountStatus
import com.llh.server.model.SysUser
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.RegisterOrUpdateVO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.service.BasicService
import org.springframework.security.core.userdetails.UserDetailsService


/**
 * SysUserService
 *
 * CreatedAt: 2020-06-01 23:27
 *
 * @author llh
 */
interface SysUserService : BasicService<SysUser>, UserDetailsService {
    /**
     * 根据用户名[username]查找数据
     */
    fun findTopByUsername(username: String): SysUser?

    /**
     * 简单信息的分页信息
     */
    fun page(queryVO: SimplePageQueryVO<SysUser>): PageDTO<SysUser>

    /**
     * 注册用户的方法
     */
    fun registerUser(userVO: RegisterOrUpdateVO): Boolean

    /**
     * 更新用户信息的方法
     */
    fun updateUser(userVO: RegisterOrUpdateVO): Boolean?

    val inactive: Int
        get() = AccountStatus.INACTIVE.ordinal
    val activation: Int
        get() = AccountStatus.ACTIVATION.ordinal
    val locked: Int
        get() = AccountStatus.LOCKED.ordinal
    val removed: Int
        get() = AccountStatus.REMOVED.ordinal
}

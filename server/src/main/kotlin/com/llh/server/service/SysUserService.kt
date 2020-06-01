package com.llh.server.service

import com.llh.server.common.constant.AccountStatus
import com.llh.server.model.SysUser


/**
 * SysUserService
 *
 * CreatedAt: 2020-06-01 23:27
 *
 * @author llh
 */
interface SysUserService : BasicService<SysUser> {
    /**
     * 根据用户名[username]查找数据
     */
    fun findTopByUsername(username: String): SysUser?

    val inactive: Int
        get() = AccountStatus.INACTIVE.ordinal
    val activation: Int
        get() = AccountStatus.ACTIVATION.ordinal
    val locked: Int
        get() = AccountStatus.LOCKED.ordinal
    val removed: Int
        get() = AccountStatus.REMOVED.ordinal
}

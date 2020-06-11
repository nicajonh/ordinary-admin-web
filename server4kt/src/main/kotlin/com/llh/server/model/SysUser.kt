package com.llh.server.model

import me.liuwj.ktorm.entity.Entity

/**
 * SysUser
 *
 * CreatedAt: 2020-06-11 21:29
 *
 * @author llh
 */
interface SysUser : BasicModel<SysUser> {
    companion object : Entity.Factory<SysUser>()

    var username: String


    var password: String

    var email: String

}

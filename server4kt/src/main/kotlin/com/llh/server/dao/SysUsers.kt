package com.llh.server.dao

import com.llh.server.model.SysUser
import me.liuwj.ktorm.schema.varchar

/**
 * SysUsers
 *
 * CreatedAt: 2020-06-11 21:38
 *
 * @author llh
 */
object SysUsers : BasicDao<SysUser>("sys_user") {
    val username by varchar("username").bindTo { it.username }
    val password by varchar("password").bindTo { it.password }
    val email by varchar("email").bindTo { it.email }
}

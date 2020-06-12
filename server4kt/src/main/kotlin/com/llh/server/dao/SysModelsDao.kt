/**
 * 系统内置数据表模型的DAO层
 */
package com.llh.server.dao

import com.llh.server.model.SysDept
import com.llh.server.model.SysUser
import me.liuwj.ktorm.schema.boolean
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

/**
 * SysUsers 用户DAO层
 *
 * CreatedAt: 2020-06-11 21:38
 *
 * @author llh
 */
object SysUsers : BasicDao<SysUser>("sys_user") {
    val username by varchar("username").bindTo { it.username }
    val password by varchar("password").bindTo { it.password }
    val email by varchar("email").bindTo { it.email }
    val accountStatus by int("account_status").bindTo { it.accountStatus }
}

/**
 * 部门DAO层
 */
object SysDepts : BasicDao<SysDept>("sys_dept") {
    val parentId by varchar("parent_id").bindTo { it.parentId }
    val deptName by varchar("dept_name").bindTo { it.deptName }
    val orderNum by int("order_num").bindTo { it.orderNum }
}

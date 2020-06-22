/**
 * 系统内置数据表模型的DAO层
 */
package com.llh.server.dao

import com.llh.server.dao.SysDictTypes.bindTo
import com.llh.server.dao.SysLogs.bindTo
import com.llh.server.dao.SysPermissions.bindTo
import com.llh.server.model.*
import me.liuwj.ktorm.schema.boolean
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.long
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

/**
 * 角色信息DAO层
 */
object SysRoles : BasicDao<SysRole>("sys_role") {
    val orderNum by int("order_num").bindTo { it.orderNum }
    val roleName by varchar("role_name").bindTo { it.roleName }
    val remark by varchar("remark").bindTo { it.remark }
    val dataScope by int("data_scope").bindTo { it.dataScope }
}

/**
 * 权限信息DAO层
 */
object SysPermissions : BasicDao<SysPermission>("sys_permission") {
    val orderNum by int("order_num").bindTo { it.orderNum }
    val permName by varchar("perm_name").bindTo { it.permName }
    val remark by varchar("remark").bindTo { it.remark }
    val parentId by varchar("parent_id").bindTo { it.parentId }
}

/**
 * 系统日志DAO层
 */
object SysLogs : BasicDao<SysLog>("sys_log") {
    val methodName by varchar("method_name").bindTo { it.methodName }
    val params by varchar("params").bindTo { it.params }
    val timeCost by long("time_cost").bindTo { it.timeCost }
    val ip by varchar("ip").bindTo { it.ip }
    val remark by varchar("remark").bindTo { it.remark }
    val url by varchar("url").bindTo { it.url }
}

/** 字典类型表DAO层 */
object SysDictTypes : BasicDao<SysDictType>("sys_dict_type") {
    val dictName by varchar("dict_name").bindTo { it.dictName }
    val dictType by varchar("dict_type").bindTo { it.dictType }
    val internalFlag by boolean("internal_flag").bindTo { it.internalFlag }
    val remark by varchar("remark").bindTo { it.remark }
}

/** 字典类型表DAO层 */
object SysDictDatas : BasicDao<SysDictData>("sys_dict_data") {
    val remark by varchar("remark").bindTo { it.remark }
    val orderNum by int("order_num").bindTo { it.orderNum }
    val dictType by varchar("dict_type").bindTo { it.dictType }
    val dictValue by varchar("dict_value").bindTo { it.dictValue }
    val dictLabel by varchar("dict_label").bindTo { it.dictLabel }
    val defaultFlag by boolean("default_flag").bindTo { it.defaultFlag }
}

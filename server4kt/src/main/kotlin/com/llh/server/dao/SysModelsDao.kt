/**
 * 系统内置数据表模型的DAO层
 */
package com.llh.server.dao

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
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val email = varchar("email").bindTo { it.email }
    val accountStatus = int("account_status").bindTo { it.accountStatus }
}

/**
 * 部门DAO层
 */
object SysDepts : BasicDao<SysDept>("sys_dept") {
    val parentId = varchar("parent_id").bindTo { it.parentId }
    val deptName = varchar("dept_name").bindTo { it.deptName }
    val orderNum = int("order_num").bindTo { it.orderNum }
}

/**
 * 角色信息DAO层
 */
object SysRoles : BasicDao<SysRole>("sys_role") {
    val orderNum = int("order_num").bindTo { it.orderNum }
    val roleName = varchar("role_name").bindTo { it.roleName }
    val remark = varchar("remark").bindTo { it.remark }
    val dataScope = int("data_scope").bindTo { it.dataScope }
}

/**
 * 权限信息DAO层
 */
object SysPermissions : BasicDao<SysPermission>("sys_permission") {
    val orderNum = int("order_num").bindTo { it.orderNum }
    val permName = varchar("perm_name").bindTo { it.permName }
    val remark = varchar("remark").bindTo { it.remark }
    val parentId = varchar("parent_id").bindTo { it.parentId }
}

/**
 * 系统日志DAO层
 */
object SysLogs : BasicDao<SysLog>("sys_log") {
    val methodName = varchar("method_name").bindTo { it.methodName }
    val params = varchar("params").bindTo { it.params }
    val timeCost = long("time_cost").bindTo { it.timeCost }
    val ip = varchar("ip").bindTo { it.ip }
    val remark = varchar("remark").bindTo { it.remark }
    val url = varchar("url").bindTo { it.url }
}

/** 字典类型表DAO层 */
object SysDictTypes : BasicDao<SysDictType>("sys_dict_type") {
    val dictName = varchar("dict_name").bindTo { it.dictName }
    val dictType = varchar("dict_type").bindTo { it.dictType }
    val internalFlag = boolean("internal_flag").bindTo { it.internalFlag }
    val remark = varchar("remark").bindTo { it.remark }
}

/** 字典类型表DAO层 */
object SysDictDatas : BasicDao<SysDictData>("sys_dict_data") {
    val remark = varchar("remark").bindTo { it.remark }
    val orderNum = int("order_num").bindTo { it.orderNum }
    val dictType = varchar("dict_type").bindTo { it.dictType }
    val dictValue = varchar("dict_value").bindTo { it.dictValue }
    val dictLabel = varchar("dict_label").bindTo { it.dictLabel }
    val defaultFlag = boolean("default_flag").bindTo { it.defaultFlag }
}

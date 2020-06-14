/**
 * 系统内置数据表模型
 */
package com.llh.server.model

import me.liuwj.ktorm.entity.Entity

/**
 * SysUser 用户
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

    var accountStatus: Int
}

/**
 * 部门
 */
interface SysDept : BasicModel<SysDept> {
    companion object : Entity.Factory<SysDept>()

    /**
     * 父部门id
     */
    var parentId: String?

    /**
     * 部门名称
     */
    var deptName: String

    /**
     * 显示顺序
     */
    var orderNum: Int

    /**
     * 子部门。
     * 不要与数据库数据进行映射
     */
    var children: MutableList<SysDept>

}

/**
 * 角色信息
 */
interface SysRole : BasicModel<SysRole> {

    /**
     * 显示顺序 order_num
     */
    var orderNum: Int

    /**
     * 角色名称 role_name
     */
    var roleName: String

    /**
     * 备注
     */
    var remark: String

}

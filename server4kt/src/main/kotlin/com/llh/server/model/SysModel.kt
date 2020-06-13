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

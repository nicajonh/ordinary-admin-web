/**
 * 系统内置数据表模型
 */
package com.llh.server.model

import com.llh.server.pojo.vo.RoleInfoVO
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
    companion object : Entity.Factory<SysRole>()

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

    /**
     * data_scope
     * 数据范围（0： 本部门数据权限; 1：本部门及其子部门数据权限; 2：全部数据权限）
     */
    var dataScope: Int

    fun convert2Entity(infoVO: RoleInfoVO): SysRole {

        if (!infoVO.id.isNullOrBlank()) {
            this.id = infoVO.id
        }
        this.roleName = infoVO.roleName
        this.dataScope = infoVO.dataScope
        this.orderNum = infoVO.orderNum
        this.remark = infoVO.remark
        return this
    }
}

/**
 * 权限信息
 */
interface SysPermission : BasicModel<SysPermission> {
    companion object : Entity.Factory<SysPermission>()

    /**
     * 权限名称 perm_name
     * 暂定格式为：`模块名:动作`
     */
    var permName: String

    /**
     * 显示顺序 order_num
     */
    var orderNum: Int

    /**
     * 备注
     */
    var remark: String

    /**
     * 父id parent_id
     */
    var parentId: String?
}

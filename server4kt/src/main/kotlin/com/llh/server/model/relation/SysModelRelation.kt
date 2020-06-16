package com.llh.server.model.relation

import me.liuwj.ktorm.entity.Entity

/**
 * RolePermRelation 角色和权限的关联表
 *
 * CreatedAt: 2020-06-16 22:01
 *
 * @author llh
 */
interface RolePermRelation : Entity<RolePermRelation> {
    var id: String

    /**
     *  角色ID role_id
     */
    var roleId: String

    /**
     *  权限ID perm_id
     */
    var permId: String

}

/**
 * 用户和角色的关联表
 */
interface UserRoleRelation : Entity<UserRoleRelation> {
    var id: String

    /**
     *  角色ID role_id
     */
    var roleId: String

    /**
     *  用户ID user_id
     */
    var userId: String
}

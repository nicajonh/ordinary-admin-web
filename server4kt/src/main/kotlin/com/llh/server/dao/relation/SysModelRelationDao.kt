/**
 * SysModelRelation 系统表下各表间关系的中间表。
 *
 * CreatedAt: 2020-06-16 22:04
 *
 * @author llh
 */
package com.llh.server.dao.relation

import com.llh.server.model.relation.RolePermRelation
import com.llh.server.model.relation.UserRoleRelation
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.varchar


object RolePermRelations : Table<RolePermRelation>("m_sys_role_perm") {
    val id by varchar("id").primaryKey().bindTo { it.id }
    val roleId by varchar("role_id").bindTo { it.roleId }
    val permId by varchar("perm_id").bindTo { it.permId }
}

object UserRoleRelations : Table<UserRoleRelation>("m_sys_role_perm") {
    val id by varchar("id").primaryKey().bindTo { it.id }
    val roleId by varchar("role_id").bindTo { it.roleId }
    val userId by varchar("user_id").bindTo { it.userId }
}


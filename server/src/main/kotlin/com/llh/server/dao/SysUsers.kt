package com.llh.server.dao

import com.llh.server.dao.SysUsers.bindTo
import com.llh.server.dao.SysUsers.primaryKey
import com.llh.server.model.BaseEntity
import com.llh.server.model.SysUser
import me.liuwj.ktorm.schema.*

/**
 * SysUsers
 *
 * CreatedAt: 2020-06-01 23:00
 *
 * @author llh
 */

abstract class BaseTable<E : BaseEntity<E>>(tableName: String) : Table<E>(tableName) {

    val id by varchar("id").primaryKey().bindTo { it.id }
    val createdAt by datetime("created_at").bindTo { it.createdAt }
    val updatedAt by datetime("updated_at").bindTo { it.updatedAt }
    val dataStatus by boolean("data_status").bindTo { it.dataStatus }
    val updatedBy by varchar("updated_by").bindTo { it.updatedBy }
    val createdBy by varchar("created_by").bindTo { it.createdBy }

}

object SysUsers : BaseTable<SysUser>("sys_user") {

    val username by varchar("username").bindTo { it.username }
    val password by varchar("password").bindTo { it.password }
    val email by varchar("email").bindTo { it.email }
    val accountStatus by int("account_status").bindTo { it.accountStatus }
}

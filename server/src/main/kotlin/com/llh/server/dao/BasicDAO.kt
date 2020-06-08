/**
 * BaseDAO
 *
 * CreatedAt: 2020-06-08 22:30
 *
 * @author llh
 */
package com.llh.server.dao

import com.llh.server.model.BasicModel
import me.liuwj.ktorm.schema.*
import kotlin.reflect.KClass


abstract class BasicTableObj<E : BasicModel<E>>(tableName: String) : Table<E>(tableName) {
    val id by varchar("id").primaryKey().bindTo { it.id }
    val createdAt by datetime("created_at").bindTo { it.createdAt }
    val updatedAt by datetime("updated_at").bindTo { it.updatedAt }
    val dataStatus by boolean("data_status").bindTo { it.dataStatus }
    val updatedBy by varchar("updated_by").bindTo { it.updatedBy }
    val createdBy by varchar("created_by").bindTo { it.createdBy }
}

abstract class BasicTableDSL<E : Any>(
    tableName: String,
    alias: String? = null,
    entityClass: KClass<E>? = null
) : BaseTable<E>(tableName, alias, entityClass) {
    val id by varchar("id").primaryKey()
    val createdAt by datetime("created_at")
    val updatedAt by datetime("updated_at")
    val updatedBy by varchar("updated_by")
    val dataStatus by boolean("data_status")
}

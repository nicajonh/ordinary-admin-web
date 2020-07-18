package com.llh.server.dao

import com.llh.server.model.BasicModel
import me.liuwj.ktorm.schema.*
import kotlin.reflect.KClass

/**
 * BasicDao
 *
 * CreatedAt: 2020-06-11 21:33
 *
 * @author llh
 */
abstract class BasicDao<E : BasicModel<E>>(tableName: String) : Table<E>(tableName) {
    val id = varchar("id").primaryKey().bindTo { it.id }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
    val updatedAt = datetime("updated_at").bindTo { it.updatedAt }
    val removeFlag = boolean("remove_flag").bindTo { it.removeFlag }
    val updatedBy = varchar("updated_by").bindTo { it.updatedBy }
    val createdBy = varchar("created_by").bindTo { it.createdBy }
}

@Deprecated("暂时没想好怎么用这个类")
abstract class BasicTableDSL<E : Any>(
    tableName: String,
    alias: String? = null,
    entityClass: KClass<E>? = null
) : BaseTable<E>(tableName, alias, entityClass) {
    val id = varchar("id").primaryKey()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    val updatedBy = varchar("updated_by")
    val removeFlag = boolean("remove_flag")
}

package com.llh.server.dao

import com.llh.server.model.SysUser
import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.*
import java.time.LocalDateTime

/**
 * SysUsers
 *
 * CreatedAt: 2020-06-01 23:00
 *
 * @author llh
 */
object SysUsers : BasicTableDSL<SysUser>("sys_user") {
    val username by varchar("username")
    val password by varchar("password")
    val email by varchar("email")
    val accountStatus by int("account_status")
    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = createEntity(row)

    @JvmStatic
    fun createEntity(row: QueryRowSet): SysUser {
        val model = SysUser(
            username = row[username].orEmpty()
            , password = row[password].orEmpty()
            , email = row[email].orEmpty()
            , accountStatus = row[accountStatus] ?: 0
        )
        model.id = row[SysDepts.id].orEmpty()
        model.updatedBy = row[SysDepts.updatedBy]
        model.dataStatus = row[SysDepts.dataStatus] ?: false
        model.createdAt = row[SysDepts.createdAt] ?: LocalDateTime.now()
        model.updatedAt = row[SysDepts.updatedAt] ?: LocalDateTime.now()
        return model
    }

}

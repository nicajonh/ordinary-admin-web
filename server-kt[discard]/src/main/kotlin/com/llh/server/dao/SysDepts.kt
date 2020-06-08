package com.llh.server.dao

import com.llh.server.model.SysDept
import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.varchar
import java.time.LocalDateTime

/**
 * SysDeptDAO
 *
 * CreatedAt: 2020-06-07 16:26
 *
 * @author llh
 */
object SysDepts : BasicTableDSL<SysDept>("sys_dept") {
    val deptName by varchar("dept_name")
    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = createEntity(row)

    @JvmStatic
    fun createEntity(row: QueryRowSet): SysDept {
        val dept = SysDept(
            deptName = row[deptName].orEmpty()
        )
        dept.id = row[id].orEmpty()
        dept.updatedBy = row[updatedBy]
        dept.dataStatus = row[dataStatus] ?: false
        dept.createdAt = row[createdAt] ?: LocalDateTime.now()
        dept.updatedAt = row[updatedAt] ?: LocalDateTime.now()
        return dept
    }


}

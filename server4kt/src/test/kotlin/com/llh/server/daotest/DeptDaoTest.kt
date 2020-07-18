package com.llh.server.daotest

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.service.sys.SysDeptService
import com.llh.server.service.sys.TableMetaInfoService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.database.asIterable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

/**
 * DeptDaoTest
 *
 * CreatedAt: 2020-06-13 10:19
 *
 * @author llh
 */
@SpringBootTest
class DeptDaoTest {
    @Autowired
    private lateinit var database: Database

    @Autowired
    @Qualifier("sysDeptService")
    private lateinit var sysDeptService: SysDeptService

    @Autowired
    @Qualifier("tableMetaInfoService")
    private lateinit var tableService: TableMetaInfoService

    @Autowired
    private lateinit var gencode: CodeGenProperties

    @Test
    fun testConfig() {
        println(gencode)
    }

    @Test
    fun findTree() {
        val tree = sysDeptService.takeTreeInfo()

        println(tree)
    }

    @Test
    fun testInfo() {
        val sql = """
SELECT
t.TABLE_NAME as TABLE_NAME,
t.TABLE_TYPE as TABLE_TYPE,
t.TABLE_SCHEMA as TABLE_SCHEMA
FROM
`information_schema`.`TABLES` AS t
where TABLE_SCHEMA = ?
        """.trimIndent()
        val names = database.useConnection { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, "admin_web")
                statement.executeQuery().asIterable().map { it.getString(1) }
            }
        }
        names.forEach { println(it) }
    }

    @Test
    fun testQuery() {
        val fetchTablesInfo = tableService.fetchTablesInfo()
        println(fetchTablesInfo)
    }

    @Test
    fun testQuery2() {
        val fetchTablesInfo = tableService.fetchColumnInfoByTableName("m_sys_role_perm")
        println(fetchTablesInfo)
    }
}

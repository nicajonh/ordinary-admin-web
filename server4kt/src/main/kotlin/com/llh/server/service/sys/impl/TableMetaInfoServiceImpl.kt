package com.llh.server.service.sys.impl

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.pojo.vo.TableColumnInfoVO
import com.llh.server.pojo.vo.TableMetaInfoVO
import com.llh.server.service.sys.TableMetaInfoService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.database.asIterable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.regex.Pattern

/**
 * TableMetaInfoServiceImpl
 *
 * CreatedAt: 2020-07-04 22:58
 *
 * @author llh
 */
@Service("tableMetaInfoService")
class TableMetaInfoServiceImpl : TableMetaInfoService {
    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var codeGenProperties: CodeGenProperties

    @Value("\${spring.datasource.druid.url}")
    private lateinit var dataConnection: String

    /**
     * 获取配置中的数据库名
     *
     * 默认返回“”。
     * 限制只能获取配置中数据库的信息。
     */
    private fun tableSchemaName(): String {
        return try {
            val pattern = Pattern.compile(".*\\d/(.*?)\\?.*")
            val matcher = pattern.matcher(dataConnection)
            matcher.matches() // 必须执行下这一步，不然下面获取不到数据
            matcher.group(1)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /** 查询字段的顺序不要乱变 */
    private val queryTablesSQL = """
        SELECT
        t.TABLE_NAME as TABLE_NAME,
        t.TABLE_TYPE as TABLE_TYPE,
        t.TABLE_SCHEMA as TABLE_SCHEMA
        FROM
        `information_schema`.`TABLES` AS t
        where TABLE_SCHEMA = ?
    """.trimIndent()

    /** 查询字段的顺序不要乱变 */
    private val queryColsSQL = """
SELECT
c.TABLE_NAME,
c.TABLE_SCHEMA,
c.COLUMN_NAME,
c.COLUMN_DEFAULT,
c.IS_NULLABLE,
c.DATA_TYPE,
c.COLUMN_TYPE,
c.COLUMN_COMMENT,
c.CHARACTER_MAXIMUM_LENGTH
FROM
`information_schema`.`COLUMNS` AS c
WHERE
c. TABLE_SCHEMA = ? and
c.TABLE_NAME = ?
""".trimIndent()

    override fun fetchTablesInfo(): List<TableMetaInfoVO> {
        return database.useConnection { connection ->
            connection.prepareStatement(queryTablesSQL).use { statement ->
                statement.setString(1, tableSchemaName())
                statement.executeQuery().asIterable().map {
                    TableMetaInfoVO(tableName = it.getString(1),
                        tableType = it.getString(2),
                        tableSchema = it.getString(3)
                    )
                }
            }
        }
    }

    override fun fetchColumnInfoByTableName(tableName: String): List<TableColumnInfoVO> {
        var querySql = queryColsSQL
        if (!codeGenProperties.ignoreFields.isNullOrBlank()) {
            // "a,b,c,d" --> `"a","b","c","d"`
            val ignoreFields = codeGenProperties.ignoreFields!!.split(",")
                .map { "\"$it\"" }.reduce { acc, s -> "$acc,$s" }
            // 应该不会有sql注入的情况吧
            querySql = "$queryColsSQL and c.COLUMN_NAME not in($ignoreFields)"
        }
        return database.useConnection { connection ->
            connection.prepareStatement(querySql).use { statement ->
                statement.setString(1, tableSchemaName())
                statement.setString(2, tableName)
                statement.executeQuery().asIterable().map {
                    TableColumnInfoVO(
                        tableName = it.getString(1),
                        tableSchema = it.getString(2),
                        columnName = it.getString(3),
                        columnDefault = it.getString(4),
                        nullable = it.getString(5),
                        dataType = it.getString(6),
                        columnType = it.getString(7),
                        columnComment = it.getString(8),
                        columnStrMax = it.getInt(9)
                    )
                }
            }
        }
    }

}

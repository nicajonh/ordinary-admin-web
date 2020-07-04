/**
 * TableMetaVO
 *
 * CreatedAt: 2020-07-04 23:31
 *
 * @author llh
 */
package com.llh.server.pojo.vo

/**
 * 数据库表的元信息
 */
data class TableMetaInfoVO(
    val tableName: String
    , val tableType: String
    , val tableSchema: String
)

/**
 * 数据表列信息
 */
data class TableColumnInfoVO(
    val tableName: String
    , val tableSchema: String
    , val columnName: String
    , val columnDefault: String?
    , val isNullable: String
    , val dataType: String
    , val columnType: String
    , val columnComment: String?
)

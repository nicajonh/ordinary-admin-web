/**
 * TableMetaVO
 *
 * CreatedAt: 2020-07-04 23:31
 *
 * @author llh
 */
package com.llh.server.pojo.vo

import com.google.common.base.CaseFormat

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
    , val nullable: String
    , val dataType: String
    , val columnType: String
    , val columnComment: String?
) {
    /** 列名转换为小驼峰写法 */
    val columnNameLowerCamel: String
        get() = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.columnName)

    /** 列名转换为大驼峰写法 */
    val columnNameUpperCamel: String
        get() = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.columnName)

    /** 列名转换为中划线写法 */
    val columnNameLowerHyphen: String
        get() = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, this.columnName)

    /** 列名转换为大驼峰写法 */
    val tableNameUpperCamel: String
        get() = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.tableName)

}

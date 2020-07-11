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
    , val columnStrMax: Int // 被转换为java中int类型了，肯定是有值的。
) {
    /** 列名转换为小驼峰写法 */
    val columnNameLowerCamel: String
        get() = convertLowerCamel(this.columnName)

    /** 列名转换为大驼峰写法 */
    val columnNameUpperCamel: String
        get() = convertUpperCamel(this.columnName)

    /** 列名转换为中划线写法 */
    val columnNameLowerHyphen: String
        get() = convertLowerHyphen(this.columnName)

    /** 列名转换为大驼峰写法 */
    val tableNameUpperCamel: String
        get() = convertUpperCamel(this.tableName)

    /** 列名转换为小驼峰写法 */
    val tableNameLowCamel: String
        get() = convertLowerCamel(this.tableName)

    /**
     * 获取列简短注释。
     * 默认以"。", "，", ",", "."为分隔符之一，只取分隔后的第一部分。
     * 如果当前列注释为空，则返回当前列的数据库的列名。
     */
    val columnCommentShort: String
        get() {
            val delimiters = listOf("。", "，", ",", ".")
            if (this.columnComment?.contains("。") == true)
                return this.columnComment.split("。")[0]
            delimiters.forEach {
                if (this.columnComment?.contains(it) == true)
                    return this.columnComment.split(it)[0]
            }
            return if (this.columnComment.isNullOrBlank())
                this.columnName
            else this.columnComment
        }


}

/**
 * 应对去前缀要求建立的类
 */
data class TableNameVO(
    val tableName: String,
    val prefix: String?
) {
    val noPrefix: String
        get() = tableNameNoPrefix()
    val noPrefixLowerCamel: String
        get() = convertLowerCamel(tableNameNoPrefix())
    val noPrefixUpperCamel: String
        get() = convertUpperCamel(tableNameNoPrefix())

    private fun tableNameNoPrefix(): String {
        prefix ?: return this.tableName
        return if (this.tableName.startsWith(prefix))
            this.tableName.substring(prefix.length until this.tableName.length)
        else this.tableName
    }


}

/** LOWER_UNDERSCORE --> LOWER_CAMEL */
private fun convertLowerCamel(text: String): String {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text)
}

/** LOWER_UNDERSCORE --> UPPER_CAMEL */
private fun convertUpperCamel(text: String): String {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text)
}

/** LOWER_UNDERSCORE --> LOWER_HYPHEN 中划线 */
private fun convertLowerHyphen(text: String): String {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, text)
}

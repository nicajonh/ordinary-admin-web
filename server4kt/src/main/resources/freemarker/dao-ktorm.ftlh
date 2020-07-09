<#assign className = tableNameVO.noPrefixUpperCamel >
/**
*  DAO层
* CreatedAt: ${.now?datetime}
* @author ${auth}
*/
object ${className}s : BasicDao<${className}>("${tableNameVO.tableName}"){
<#list cols as item >
    val ${item.columnNameLowerCamel} = ${item.dataType}("${item.columnName}").bindTo { it.${item.columnNameLowerCamel} }
</#list>
}

<#assign ClassName = tableNameVO.noPrefixUpperCamel >
<#assign className = tableNameVO.noPrefixLowerCamel >
<#if customInfo.javaPackage??>
    <#lt>package ${customInfo.javaPackage}.dao
</#if>
/**
*  DAO层
* CreatedAt: ${.now?datetime}
* @author ${auth}
*/
object ${ClassName}s : BasicDao<${ClassName}>("${tableNameVO.tableName}"){
<#list cols as item >
    val ${item.columnNameLowerCamel} = <#rt>
    <#lt>${typeConvert(item.dataType , item.columnType)}("${item.columnName}")<#rt>
    <#lt>.bindTo { it.${item.columnNameLowerCamel} }
</#list>
}

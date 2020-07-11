<#assign ClassName = tableNameVO.noPrefixUpperCamel >
<#assign className = tableNameVO.noPrefixLowerCamel >
/**
* ${customInfo.modelDescription}数据库模型类
* CreatedAt: ${.now?datetime}
* @author ${auth}
*/
interface ${ClassName} : BasicModel<${ClassName}> {
    /** 伴生对象 */
    companion object : Entity.Factory<${ClassName}>()
<#list cols as item >
    /** ${item.columnComment}   ${item.columnName}  */
    var ${item.columnNameLowerCamel}: <#rt>
    <#lt>${typeConvert(item.dataType , item.columnType)}<#rt>
    <#lt><#if item.nullable?upper_case == "YES" >?</#if>
</#list>
}


<#assign aDatetime = .now>
/**
* CreatedAt: ${aDatetime?datetime}
* @author ${auth}
*/
interface ${cols[0].tableNameUpperCamel} : BasicModel<${cols[0].tableNameUpperCamel}> {
    /** 伴生对象 */
    companion object : Entity.Factory<${cols[0].tableNameUpperCamel}>()
<#list cols as item >
    /** ${item.columnComment}   ${item.columnName}  */
    var ${item.columnNameLowerCamel}: ${typeConvert(item.dataType , item.columnType)}<#if item.nullable?upper_case == "YES" >?</#if>
</#list>
}


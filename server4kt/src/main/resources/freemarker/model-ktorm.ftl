interface ${cols[0].tableNameUpperCamel} : BasicModel<${cols[0].tableNameUpperCamel}> {
companion object : Entity.Factory<${cols[0].tableNameUpperCamel}>()
<#list cols as item >
    /** ${item.columnComment}   ${item.columnName}  */
    var ${item.columnNameLowerCamel}: String<#if item.nullable?upper_case == "YES" >?</#if>
</#list>
}

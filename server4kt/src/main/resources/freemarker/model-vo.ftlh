<#assign ClassName = tableNameVO.noPrefixUpperCamel >
<#assign className = tableNameVO.noPrefixLowerCamel >
<#if customInfo.javaPackage??>
    <#lt>package ${customInfo.javaPackage}.vo
</#if>

<#-- #rt #lt 两个指令连用可以生成的代码中无空格和换行 -->
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
* ${customInfo.modelDescription}数据库模型VO类
* ${ClassName}VO
* CreatedAt: ${.now?datetime}
* @author ${auth}
*/
@ApiModel(description = "${customInfo.modelDescription}的VO类")
data class ${ClassName}VO(
<#list cols as item >
    @ApiModelProperty(value = "${item.columnCommentShort}"<#rt>
        <#if item.nullable?upper_case == "NO" >
            <#lt>,required=true<#rt>
        </#if><#rt>
    <#lt>)
    val ${item.columnNameLowerCamel}: ${typeConvert(item.dataType , item.columnType)}<#rt>
    <#lt><#if item.nullable?upper_case == "YES" >?</#if><#if item_has_next>,</#if>
</#list>
)

<#assign ClassName = tableNameVO.noPrefixUpperCamel >
<#assign className = tableNameVO.noPrefixLowerCamel >
<#if customInfo.javaPackage??>
   <#lt>package ${customInfo.javaPackage}.controller
</#if>

import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * ${ClassName}Controller
 *
 * CreatedAt: ${.now?datetime}
 *
 * @author ${auth}
 */
@RestController
@RequestMapping("${customInfo.urlPrefix}")
@Api("${customInfo.modelDescription}的API。")
class SysPermissionController {

    @Autowired
    @Qualifier("${className}Service")
    private lateinit var ${className}Service: ${ClassName}Service

    @GetMapping("{${className}Id}")
    @ApiOperation("根据${customInfo.modelDescription}id获取${customInfo.modelDescription}信息。")
    fun getOneById(@PathVariable("${className}Id") entityId: String): JsonWrapper {
        val entity = ${className}Service.findById(entityId)
        return okResponse(entity)
    }

    @DeleteMapping("delete/{${className}Id}")
    @ApiOperation("根据${customInfo.modelDescription}id移除${customInfo.modelDescription}信息。")
    fun removeById(@PathVariable("${className}Id") entityId: String): JsonWrapper {
        val removed = ${className}Service.remove(id = entityId)
        return okResponse(removed)
    }

    @PostMapping("page")
    @ApiOperation("获取${customInfo.modelDescription}列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<${ClassName}>): JsonWrapper {
        val page = ${className}Service.page(pageQueryVO)
        return okResponse(page)
    }

    @PostMapping
    @ApiOperation("新增一个${customInfo.modelDescription}列表。")
    fun addModel(@RequestBody infoVO: ${ClassName}VO): JsonWrapper {
        val saved: Boolean = ${className}Service.saveByVO(infoVO)
        return okResponse(saved)
    }

}

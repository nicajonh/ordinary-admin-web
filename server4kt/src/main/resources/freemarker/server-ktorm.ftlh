import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.service.BasicService
<#assign aDatetime = .now>
/**
 * SysLogService
 *
 * CreatedAt: ${aDatetime?datetime}
 *
 * @author llh
 */
interface ${cols[0].tableNameUpperCamel}Service : BasicService<${cols[0].tableNameUpperCamel}> {
    /**
     * 简单信息的分页信息
     */
    fun page(queryVO: SimplePageQueryVO<${cols[0].tableNameUpperCamel}>): PageDTO<${cols[0].tableNameUpperCamel}>
}

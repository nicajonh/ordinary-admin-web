import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.service.ServiceHelper
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
<#assign aDatetime = .now>
<#-- TODO 测试下这种写法能用不？ -->
<#assign className = cols[0].tableNameUpperCamel>
<#assign daoName = cols[0].tableNameUpperCamel+'s'>
/**
 * ${cols[0].tableNameUpperCamel}ServiceImpl
 *
 * CreatedAt: ${.now?datetime}
 *
 * @author ${auth}
 */
@Service("${cols[0].tableNameLowCamel}Service")
class ${className}ServiceImpl : ServiceHelper<${className}>(), {className}Service, Logging {

    @Autowired
    private lateinit var database: Database


    override fun save(entity: ${className}): ${className} {
        initValueForModelToDB(entity)
        database.sequenceOf(${daoName}).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(${daoName}) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
                it.removeFlag to persistence
            }
        }
        return updated > 0
    }

    override fun updateById(entity: ${className}): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): ${className}? {
        val find = database.sequenceOf(${daoName}).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            <#-- ${r"${id}"} 原样输出  -->
            logger.warn("not find ${className}(id:${r"${id}"} ) info")
        return find
    }


    override fun page(queryVO: SimplePageQueryVO<${className}>): PageDTO<${className}> {
        return pageQuery(queryVO)
    }


    // ----------------- private fun  ------------------

    private fun pageQuery(queryVO: SimplePageQueryVO<${className}>): PageDTO<${className}> {
        var total = 0
        val query = database.from(${daoName})
            .select(${daoName}.columns) // 指定需要查询的列
            .whereWithConditions {
                // 添加查询条件
                it += ${daoName}.removeFlag eq persistence
            }.limit(queryVO.pageStartIndex(), queryVO.pageSize)
            .orderBy(orderCondition(${daoName}, queryVO))
            .map { row ->
                total = row.query.totalRecords
                ${daoName}.createEntity(row)
                }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = queryVO.pageSize
            )
    }


}

package com.llh.server.service.sys.impl

import com.llh.server.pojo.vo.TableColumnInfoVO
import com.llh.server.service.sys.GenCodeService
import com.llh.server.service.sys.TableMetaInfoService
import freemarker.template.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils

/**
 * GenCodeServiceImpl
 *
 * CreatedAt: 2020-07-06 21:40
 *
 * @author llh
 */
@Service
class GenCodeServiceImpl : GenCodeService {

    @Autowired
    private lateinit var configuration: Configuration

    @Autowired
    @Qualifier("tableMetaInfoService")
    private lateinit var tableMetaInfoService: TableMetaInfoService

    override fun genCodeByTableName(tableName: String): String {
        val colsInfo = tableMetaInfoService.fetchColumnInfoByTableName(tableName)
        return genModel4ktorm(colsInfo)
    }

    private fun genModel4ktorm(colsInfo: List<TableColumnInfoVO>): String {
        val template = configuration.getTemplate("model-ktorm.ftl")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template, mapOf("cols" to colsInfo))
    }
}

package com.llh.server.service.sys.impl

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.common.util.ConvertType4Kt
import com.llh.server.pojo.vo.TableColumnInfoVO
import com.llh.server.pojo.vo.TableNameVO
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
    private lateinit var codeGenProperties: CodeGenProperties

    @Autowired
    @Qualifier("tableMetaInfoService")
    private lateinit var tableMetaInfoService: TableMetaInfoService

    override fun genCodeByTableName(tableName: String): String {
        val colsInfo = tableMetaInfoService.fetchColumnInfoByTableName(tableName)
//        return genModel4ktorm(colsInfo)
//        return genServiceImpl4ktorm(colsInfo)
//        return genModelVO4kotlin(colsInfo)
        return genDAO4kotlin(colsInfo, tableName)
    }

    // ------------------------- private fun  -----------------------------
    private fun genModel4ktorm(colsInfo: List<TableColumnInfoVO>): String {
        val template = configuration.getTemplate("model-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genServiceImpl4ktorm(colsInfo: List<TableColumnInfoVO>): String {
        // service-impl-ktorm.ftlh
        val template = configuration.getTemplate("service-impl-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genModelVO4kotlin(colsInfo: List<TableColumnInfoVO>): String {
        val template = configuration.getTemplate("model-vo.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genDAO4kotlin(colsInfo: List<TableColumnInfoVO>, tableName: String): String {
        val template = configuration.getTemplate("dao-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(tableName, codeGenProperties.tablePrefix),
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

//    private fun noPrefixTableName(prefix:String? ,tableName: String) :String {
//        if ()
//    }
}

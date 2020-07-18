package com.llh.server.service.codegen.impl

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.common.util.Convert4KtormBindFun
import com.llh.server.common.util.ConvertType4Kt
import com.llh.server.pojo.vo.CodeGenVO
import com.llh.server.pojo.vo.TableColumnInfoVO
import com.llh.server.pojo.vo.TableMetaInfoVO
import com.llh.server.pojo.vo.TableNameVO
import com.llh.server.service.codegen.GenCodeService
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

    override fun genCodeByTableName(codeGenVO: CodeGenVO): MutableMap<String, String> {
        val colsInfo = tableMetaInfoService.fetchColumnInfoByTableName(codeGenVO.tableName)
        val fileAndCodeMap = mutableMapOf<String, String>()
        fileAndCodeMap["model.kt"] = genModel4ktorm(colsInfo, codeGenVO)
        fileAndCodeMap["serviceImpl.kt"] = genServiceImpl4ktorm(colsInfo, codeGenVO)
        fileAndCodeMap["modelvo.kt"] = genModelVO4kotlin(colsInfo, codeGenVO)
        fileAndCodeMap["dao.kt"] = genDAO4kotlin(colsInfo, codeGenVO)
        fileAndCodeMap["controller.kt"] = genController(colsInfo, codeGenVO)
        return fileAndCodeMap
    }

    override fun fetchTables(): List<TableMetaInfoVO> {
        return tableMetaInfoService.fetchTablesInfo()
    }

    // ------------------------- private fun  -----------------------------
    private fun genModel4ktorm(colsInfo: List<TableColumnInfoVO>, codeGenVO: CodeGenVO): String {
        val template = configuration.getTemplate("model-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(codeGenVO.tableName, codeGenProperties.tablePrefix),
                    "customInfo" to codeGenVO,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genServiceImpl4ktorm(colsInfo: List<TableColumnInfoVO>, codeGenVO: CodeGenVO): String {
        // service-impl-ktorm.ftlh
        val template = configuration.getTemplate("service-impl-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(codeGenVO.tableName, codeGenProperties.tablePrefix),
                    "customInfo" to codeGenVO,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genModelVO4kotlin(colsInfo: List<TableColumnInfoVO>, codeGenVO: CodeGenVO): String {
        val template = configuration.getTemplate("model-vo.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(codeGenVO.tableName, codeGenProperties.tablePrefix),
                    "customInfo" to codeGenVO,
                    "typeConvert" to ConvertType4Kt(codeGenProperties.typeMap),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genDAO4kotlin(colsInfo: List<TableColumnInfoVO>, codeGenVO: CodeGenVO): String {
        val template = configuration.getTemplate("dao-ktorm.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(codeGenVO.tableName, codeGenProperties.tablePrefix),
                    "customInfo" to codeGenVO,
                    "typeConvert" to Convert4KtormBindFun(),
                    "auth" to codeGenProperties.auth)
            )
    }

    private fun genController(colsInfo: List<TableColumnInfoVO>, codeGenVO: CodeGenVO): String {
        val template = configuration.getTemplate("controller.ftlh")
        return FreeMarkerTemplateUtils
            .processTemplateIntoString(template,
                mapOf("cols" to colsInfo,
                    "tableNameVO" to TableNameVO(codeGenVO.tableName, codeGenProperties.tablePrefix),
                    "customInfo" to codeGenVO,
                    "auth" to codeGenProperties.auth)
            )
    }

}

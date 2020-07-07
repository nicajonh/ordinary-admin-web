/**
 * FreemarkerFunc
 *
 * CreatedAt: 2020-07-07 21:37
 *
 * @author llh
 */
package com.llh.server.common.util

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.pojo.vo.TableColumnInfoVO
import freemarker.template.TemplateMethodModelEx
import freemarker.template.TemplateModelException
import freemarker.template.TemplateScalarModel


/**
 * 将MySQL中的数据类类型转换为kt
 * 默认返回“Any”
 * 必须转入两个参数。一是[TableColumnInfoVO.dataType]
 * 二是[TableColumnInfoVO.columnType]
 */
class ConvertType4Kt(private val typeMap: Map<String, String>?) : TemplateMethodModelEx {

    override fun exec(arguments: MutableList<Any?>?): Any {
        if (arguments.isNullOrEmpty() || arguments.size != 2) {
            throw  TemplateModelException(
                "Error: Expecting 1 string arguments here")
        }
        val dataType = (arguments[0] as TemplateScalarModel).asString
        val colType = (arguments[1] as TemplateScalarModel).asString
        if (dataType.toLowerCase() == "tinyint" && colType.toLowerCase() == "tinyint(1)")
            return "Boolean"
        return typeMap?.get(dataType.toUpperCase()) ?: "Any"
    }

}

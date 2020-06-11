package com.llh.server.pojo

import com.llh.server.common.constant.BasicExp
import com.llh.server.common.constant.SysExpInfo
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * JsonWrapper 数据响应包装类。
 *
 * CreatedAt: 2020-06-06 15:09
 *
 * @author llh
 */
@ApiModel("数据响应包装类")
data class JsonWrapper(
    @ApiModelProperty("自定义响应代码")
    val code: Int,
    @ApiModelProperty("响应消息")
    val msg: String,
    @ApiModelProperty("响应数据区域")
    val data: Any?
)

/**
 * 正常响应的消息。
 */
fun okResponse(data: Any?, msg: String = BasicExp.SUCCESS.msg): JsonWrapper {
    return JsonWrapper(BasicExp.SUCCESS.code, msg, data)
}

/**
 * 异常响应消息。
 */
fun responseExp(exp: SysExpInfo, msg: String = exp.getSelfMsg(), data: Any? = null): JsonWrapper {
    return JsonWrapper(exp.getSelfCode(), msg, data)
}

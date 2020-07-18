/**
 * CodeGenVO
 *
 * CreatedAt: 2020-07-11 10:09
 *
 * @author llh
 */
package com.llh.server.pojo.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("代码生成配置。")
data class CodeGenVO(
    @ApiModelProperty("模块简述")
    val modelDescription: String,
    @ApiModelProperty("url前缀")
    val urlPrefix: String,
    @ApiModelProperty("数据库表名")
    val tableName: String,
    @ApiModelProperty("java包名")
    val javaPackage: String?
)

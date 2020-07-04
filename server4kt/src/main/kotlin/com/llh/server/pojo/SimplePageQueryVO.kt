package com.llh.server.pojo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * SimplePageQueryVO
 *
 * CreatedAt: 2020-06-06 20:48
 *
 * @author llh
 */
@ApiModel("简单的分页查询参数查询类。")
data class SimplePageQueryVO<T>(
    @ApiModelProperty(value = "页数", required = true)
    val pageNumber: Int,
    @ApiModelProperty(value = "每页数量", required = true)
    val pageSize: Int,
    @ApiModelProperty(value = "排序字段", required = true)
    val orderField: String = "updatedAt",
    @ApiModelProperty(value = "排序方式。asc or desc", required = true)
    val orderType: String = "asc",
    @ApiModelProperty(value = "查询条件。简单查询都是查询模型类的字段。")
    val model: T?
) {
    fun pageStartIndex(): Int {
        return if ((pageNumber - 1) <= 0) 0 else (pageNumber - 1) * pageSize
    }
}

data class PageQueryInfo(
    @ApiModelProperty(value = "页数", required = true)
    val pageNumber: Int,
    @ApiModelProperty(value = "每页数量", required = true)
    val pageSize: Int,
    @ApiModelProperty(value = "排序字段", required = true)
    val orderField: String,
    @ApiModelProperty(value = "排序方式。asc or desc", required = true)
    val orderType: String = "asc"
)

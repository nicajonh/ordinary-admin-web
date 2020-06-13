/**
 * DeptVO
 *
 * CreatedAt: 2020-06-13 08:43
 *
 * @author llh
 */
package com.llh.server.pojo.vo

import io.swagger.annotations.ApiModel

@ApiModel("部门信息的VO类")
data class DeptInfoVO(
    val id: String?
    , val parentId: String?
    , val deptName: String
    , val orderNum: Int
)

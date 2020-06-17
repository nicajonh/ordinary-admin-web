/**
 * PermissionVO
 *
 * CreatedAt: 2020-06-17 23:06
 *
 * @author llh
 */
package com.llh.server.pojo.vo


data class PermissionInfoVO(
    val orderNum: Int
    , val id: String?
    , val parentId: String?
    , val permName: String
    , val remark: String
)

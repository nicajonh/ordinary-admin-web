/**
 * RoleVO
 *
 * CreatedAt: 2020-06-15 21:43
 *
 * @author llh
 */
package com.llh.server.pojo.vo


data class RoleInfoVO(
    val orderNum: Int
    , val roleName: String
    , val remark: String
    , val dataScope: Int
    , val id: String?
)

/**
 * CodeGenVO
 *
 * CreatedAt: 2020-07-11 10:09
 *
 * @author llh
 */
package com.llh.server.pojo.vo


data class CodeGenVO(
    val modelDescription: String,
    val urlPrefix: String,
    val tableName: String,
    val javaPackage: String?
)

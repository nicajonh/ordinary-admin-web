/**
 * DictVO
 *
 * CreatedAt: 2020-06-23 21:33
 *
 * @author llh
 */
package com.llh.server.pojo.vo


data class DictTypeVO(
    val dictName: String
    , val dictType: String
    , val internalFlag: Boolean = false
    , val remark: String?
    , val id: String?
)

data class DictDataVO(
    val dictLabel: String
    , val dictValue: String
    , val dictType: String
    , val orderNum: Int = 0
    , val defaultFlag: Boolean = false
    , val remark: String?
    , val id: String?
)

package com.llh.server.service.sys

/**
 * GenCodeService
 *
 * CreatedAt: 2020-07-06 21:39
 *
 * @author llh
 */
interface GenCodeService {
    fun genCodeByTableName(tableName: String): String
}

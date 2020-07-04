package com.llh.server.service.sys

import com.llh.server.pojo.vo.TableColumnInfoVO
import com.llh.server.pojo.vo.TableMetaInfoVO

/**
 * TableMetaInfoService 数据库中的数据表元信息
 *
 * CreatedAt: 2020-07-04 22:57
 *
 * @author llh
 */
interface TableMetaInfoService {

    /**
     * 获取当前数据库的表信息。
     */
    fun fetchTablesInfo(): List<TableMetaInfoVO>

    /**
     * 根据数据表名[tableName]获取列信息
     */
    fun fetchColumnInfoByTableName(tableName: String): List<TableColumnInfoVO>
}

package com.llh.server.service.codegen

import com.llh.server.pojo.vo.CodeGenVO
import com.llh.server.pojo.vo.TableMetaInfoVO

/**
 * GenCodeService
 *
 * CreatedAt: 2020-07-06 21:39
 *
 * @author llh
 */
interface GenCodeService {
    fun genCodeByTableName(codeGenVO: CodeGenVO): MutableMap<String, String>

    /** 生成vue框架的代码。 */
    @Deprecated("测试用的。测完了删除。")
    fun genCodeByTableNameForVue(codeGenVO: CodeGenVO): MutableMap<String, String>

    /** 获取当前数据库的所有表信息 */
    fun fetchTables(): List<TableMetaInfoVO>
}

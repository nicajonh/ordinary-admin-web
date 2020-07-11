package com.llh.server.service.codegen

import com.llh.server.pojo.vo.CodeGenVO

/**
 * GenCodeService
 *
 * CreatedAt: 2020-07-06 21:39
 *
 * @author llh
 */
interface GenCodeService {
    fun genCodeByTableName(codeGenVO: CodeGenVO): MutableMap<String, String>
}

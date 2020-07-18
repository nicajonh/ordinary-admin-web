package com.llh.server.controller

import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.CodeGenVO
import com.llh.server.service.codegen.GenCodeService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * CodeGenController
 *
 * CreatedAt: 2020-07-16 22:01
 *
 * @author llh
 */
@RestController
@RequestMapping("code-gen")
@Api("代码生成模块。")
class CodeGenController {

    @Autowired
    private lateinit var genCodeService: GenCodeService

    @GetMapping("tables")
    fun tables(): JsonWrapper {
        return okResponse(genCodeService.fetchTables())
    }

    @PostMapping("gen")
    fun genCode(@RequestBody codeGenVO: CodeGenVO): JsonWrapper {
        return okResponse(genCodeService.genCodeByTableName(codeGenVO))
    }
}

package com.llh.server.servicetest

import com.llh.server.common.config.CodeGenProperties
import com.llh.server.pojo.vo.CodeGenVO
import com.llh.server.service.codegen.GenCodeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * GenCodeTest
 *
 * CreatedAt: 2020-07-06 22:07
 *
 * @author llh
 */
@SpringBootTest
class GenCodeTest {
    @Autowired
    private lateinit var genCodeService: GenCodeService

    @Autowired
    private lateinit var codeGenProperties: CodeGenProperties
    @Test
    fun testProperties(){

        println(codeGenProperties)
    }

    @Test
    fun testGen() {
        val codeGenVO = CodeGenVO(tableName = "sys_dict_type",
            modelDescription = "用户", urlPrefix = "user",javaPackage = "com.llh.server")
        val gened = genCodeService.genCodeByTableName(codeGenVO)
        println(gened)
    }

    @Test
    fun testGenVue() {
        val codeGenVO = CodeGenVO(tableName = "sys_dict_type",
            modelDescription = "用户", urlPrefix = "user",javaPackage = "com.llh.server")
        val gened = genCodeService.genCodeByTableNameForVue(codeGenVO)
        println(gened)
    }
}

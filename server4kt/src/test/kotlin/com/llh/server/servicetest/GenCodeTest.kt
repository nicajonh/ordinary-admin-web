package com.llh.server.servicetest

import com.llh.server.service.sys.GenCodeService
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

    @Test
    fun testGen() {
        val gened = genCodeService.genCodeByTableName("sys_dict_data")
        println(gened)
    }
}

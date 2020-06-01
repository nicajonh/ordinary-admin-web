package com.llh.server.daotest

import com.llh.server.dao.SysUsers
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.findById
import me.liuwj.ktorm.entity.sequenceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * SysUserTest
 *
 * CreatedAt: 2020-06-01 23:05
 *
 * @author llh
 */
@SpringBootTest
class SysUserTest {
    @Autowired
    lateinit var database: Database

    @Test
    fun testQuery() {
        val user = database.sequenceOf(SysUsers)
            .find { it.id eq "b258e8ba-d642-4bb8-bf91-782d20cf2177" }
        println(user)
    }
}

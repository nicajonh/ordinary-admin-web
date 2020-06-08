package com.llh.server.daotest

import com.llh.server.dao.SysDepts
import com.llh.server.dao.SysUsers
import com.llh.server.model.SysDept
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.sequenceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*

/**
 * SysDeptDAOTest
 *
 * CreatedAt: 2020-06-07 16:38
 *
 * @author llh
 */
@SpringBootTest
class SysDeptDAOTest {
    @Autowired
    lateinit var database: Database


    @Test
    fun testQuery() {
        val q = database.from(SysDepts).select(SysDepts.columns)
            .where { SysDepts.deptName eq "a" }
            .map { row -> SysDepts.createEntity(row) }
        println(q)
    }
}

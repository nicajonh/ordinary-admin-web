package com.llh.server.daotest

import com.llh.server.dao.SysDepts
import com.llh.server.model.SysDept
import com.llh.server.service.sys.SysDeptService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.from
import me.liuwj.ktorm.dsl.select
import me.liuwj.ktorm.dsl.where
import me.liuwj.ktorm.entity.sequenceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

/**
 * DeptDaoTest
 *
 * CreatedAt: 2020-06-13 10:19
 *
 * @author llh
 */
@SpringBootTest
class DeptDaoTest {
    @Autowired
    private lateinit var database: Database

    @Autowired
    @Qualifier("sysDeptService")
    private lateinit var sysDeptService: SysDeptService

    @Test
    fun findTree() {
        val tree = sysDeptService.takeTreeInfo()
        println(tree)

    }
}

package com.llh.server.controller

import com.llh.server.dao.SysDepts
import com.llh.server.model.SysDept
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.from
import me.liuwj.ktorm.dsl.select
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * SysDeptController
 *
 * CreatedAt: 2020-06-08 21:08
 *
 * @author llh
 */
@RestController
@RequestMapping("test")
class SysDeptController {
    @Autowired
    private lateinit var database: Database

    @GetMapping("list")
    fun list(): List<SysDept> {
        return database.from(SysDepts).select(SysDepts.columns)
            .map { row -> SysDepts.createEntity(row) }

    }
}

package com.llh.server.servertest

import com.llh.server.model.SysUser
import com.llh.server.service.SysUserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * SysUserService
 *
 * CreatedAt: 2020-06-01 23:46
 *
 * @author llh
 */
@SpringBootTest
class SysUserServiceTest {
    @Autowired
    @Qualifier("sysUserService")
    private lateinit var sysUserService: SysUserService


    @Test
    fun testFindTopByUsername() {
        val u = sysUserService.findTopByUsername("Tom")
        println(u)
    }
    @Test
    @Transactional
    @Rollback
    fun testUpdate(){
        val u = SysUser()
        u.username = "Lucy"
        u.email = "ha@b.c"
        u.id = "ea9446eb-c54e-418f-ab0f-7b196aadf6e5"
        val updateById = sysUserService.updateById(u)
        println(updateById)
    }

    @Test
    @Transactional
    @Rollback
    fun testAdd() {
        val u = SysUser()
        u.id = UUID.randomUUID().toString()
        u.username = "Lucy"
        u.email = "ha@b.c"
        val save = sysUserService.save(u)
        println(save)
    }
    @Test
    fun testRemove() {
        val remove = sysUserService.remove("a669bb16-ed35-410f-bd72-3de594c89e3a")
        println(remove)
    }

}

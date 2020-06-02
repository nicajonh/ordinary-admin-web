package com.llh.server.kotlintest

import com.llh.server.model.SysUser
import com.llh.server.model.copyProperties
import org.junit.jupiter.api.Test

/**
 * DemoTest
 *
 * CreatedAt: 2020-06-02 22:00
 *
 * @author llh
 */
class DemoTest {

    @Test
    fun testCopy() {
        val u1 = SysUser()
        val u2 = SysUser()
        u1.email = "fff@a.com"
        u1.username = "Tom"
        u2.copyProperties(u1)
        println(u2)
    }
}

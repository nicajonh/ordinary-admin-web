package com.llh.server.tooltest

import com.llh.server.common.util.SnowFlake4kt
import com.llh.server.common.util.uuidStr
import org.junit.jupiter.api.Test

/**
 * UUIDTest
 *
 * CreatedAt: 2020-06-20 23:49
 *
 * @author llh
 */
class UUIDTest {
    @Test
    fun testGen() {
        val set = mutableSetOf<String>()
        for (index in 1..100) {
            set.add(uuidStr())
        }
        println(set.size)
    }

    @Test
    fun testGen2() {
        SnowFlake4kt.initConfig(2, 4)
        val set = mutableSetOf<Long>()
        for (index in 1..10000) {
            set.add(SnowFlake4kt.nextId())
        }
        println(set.size)
    }

}



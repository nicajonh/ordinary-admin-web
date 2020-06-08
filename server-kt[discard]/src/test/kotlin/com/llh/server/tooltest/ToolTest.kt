package com.llh.server.tooltest

import com.llh.server.common.util.JwtTokenUtil
import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.xml.bind.DatatypeConverter
import kotlin.collections.HashMap

/**
 * ToolTest
 *
 * CreatedAt: 2020-06-04 21:39
 *
 * @author llh
 */
@SpringBootTest
class ToolTest {
    @Autowired
    private lateinit var uitl: JwtTokenUtil

    @Test
    fun testInitTools() {
        val numbersMap = mutableMapOf("username" to "username-k", "key2" to "2", "key3" to "3", "key4" to "1")
        val token = uitl.generateAccessToken("llh", numbersMap)
        println(token)
        Assertions.assertTrue(uitl.validateToken(token))
        Assertions.assertEquals(uitl.extractUsername(token), "username-k")
        Assertions.assertNotEquals(uitl.extractRemainingTime(token), 0)
        Assertions.assertEquals(uitl.extractUserId(token), "llh")
    }


}

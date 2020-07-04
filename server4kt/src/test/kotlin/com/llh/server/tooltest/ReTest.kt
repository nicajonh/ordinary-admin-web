package com.llh.server.tooltest

import com.google.common.base.CaseFormat
import me.liuwj.ktorm.support.mysql.match
import org.junit.jupiter.api.Test
import java.util.regex.Pattern

/**
 * ReTest
 *
 * CreatedAt: 2020-07-04 23:12
 *
 * @author llh
 */
class ReTest {

    @Test
    fun testMatch() {
//        val re = Regex("\\d/(.*?)\\?")
        val text = "jdbc:mysql://localhost:3306/admin_web?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8"
//        val find = re.find(text)
//        println(find?.value)
//        val matchEntire = re.matchEntire(text)
//        println(matchEntire?.value)
        val pattern = Pattern.compile(".*\\d/(.*?)\\?.*")
        val matcher = pattern.matcher(text)
//        println(matcher.matches())
        println(matcher.group(1))

    }

    @Test
    fun testTools() {
        val rs = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "loveYou")
        println(rs)

    }
}

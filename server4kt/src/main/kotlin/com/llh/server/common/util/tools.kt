/**
 * tools
 *
 * CreatedAt: 2020-06-08 22:08
 *
 * @author llh
 */
package com.llh.server.common.util

import cn.hutool.core.util.StrUtil


fun uuidStr(): String {
    return "${SnowFlake(2, 3).nextId()}"
}

object StrTools {
    fun isBlank(str: String?): Boolean {
        str ?: return false
        return StrUtil.isBlank(str)
    }
}

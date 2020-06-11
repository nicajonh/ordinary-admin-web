package com.llh.server.common.exception

import com.llh.server.common.constant.SysExpInfo
import java.lang.RuntimeException

/**
 * BasicException
 *
 * CreatedAt: 2020-06-06 15:55
 *
 * @author llh
 */
class BasicException(private val expInfo: SysExpInfo, msg: String = expInfo.getSelfMsg()) :
    RuntimeException(msg) {

    fun msgException(): String {
        return expInfo.getSelfMsg()
    }
    fun codeException(): Int {
        return expInfo.getSelfCode()
    }

}

package com.llh.server.common.util

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

/**
 * ServletUtils Servlet工具类
 *
 * CreatedAt: 2020-06-21 16:57
 *
 * @author llh
 */
object ServletUtils : Logging {

    /**
     * 获取Request属性
     */
    fun getRequestAttributes(): ServletRequestAttributes {
        return RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
    }

    /**
     * 获取request对象
     */
    fun getRequest(): HttpServletRequest {
        return getRequestAttributes().request
    }

    /**
     * 获取IP地址
     * 参考[写法][https://gitee.com/-/ide/project/renrenio/renren-fast/edit/master/-/src/main/java/io/renren/common/utils/IPUtils.java]
     */
    fun getIpAddr(request: HttpServletRequest = getRequest()): String? {
        return try {
            var temp = request.getHeader("x-forwarded-for")
            if (temp.isEmpty() || "unknown".equals(temp, true))
                temp = request.getHeader("Proxy-Client-IP")
            if (temp.isEmpty() || "unknown".equals(temp, true))
                temp = request.getHeader("WL-Proxy-Client-IP")
            if (temp.isEmpty() || "unknown".equals(temp, true))
                temp = request.getHeader("HTTP_CLIENT_IP")
            if (temp.isEmpty() || "unknown".equals(temp, true))
                temp = request.getHeader("HTTP_X_FORWARDED_FOR")
            if (temp.isEmpty() || "unknown".equals(temp, true))
                temp = request.remoteAddr
            return temp
        } catch (e: Exception) {
            logger.error("IPUtils ERROR", e)
            null
        }
    }
}

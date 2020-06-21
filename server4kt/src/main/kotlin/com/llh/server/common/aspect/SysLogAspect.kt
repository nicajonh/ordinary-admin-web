package com.llh.server.common.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import com.llh.server.common.annotation.SysLogAnnotation
import com.llh.server.common.util.ServletUtils
import com.llh.server.model.SysLog
import com.llh.server.service.sys.SysLogService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * SysLogAspect 系统日志，切面处理类
 *
 * CreatedAt: 2020-06-21 15:57
 *
 * @author llh
 */
@Aspect
@Component
class SysLogAspect {

    @Autowired
    @Qualifier("sysLogService")
    private lateinit var sysLogService: SysLogService

    @Pointcut("@annotation(com.llh.server.common.annotation.SysLogAnnotation)")
    fun logPointCut() {
    }

    @Around("logPointCut()")
    fun around(point: ProceedingJoinPoint): Any? {
        val beginTime = System.currentTimeMillis()
        // 执行方法
        val result = point.proceed()
        // 执行时长(毫秒)
        val timeCost = System.currentTimeMillis() - beginTime
        // 保存日志
        sysLogService.save(createSysLogEntity(point, timeCost))
        return result
    }

    private fun createSysLogEntity(joinPoint: ProceedingJoinPoint, timeCost: Long): SysLog {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val sysLog = SysLog()
        val logAnnotation = method.getAnnotation(SysLogAnnotation::class.java)
        if (logAnnotation.value.isNotEmpty()) {
            // 注解上的描述
            sysLog.remark = logAnnotation.value
        }
        val className = joinPoint.target.javaClass.name
        val methodName = signature.name
        sysLog.methodName = "$className.$methodName()"
        //请求的参数
        sysLog.params = try {
            val args = joinPoint.args
            ObjectMapper().writeValueAsString(args)
        } catch (e: Exception) {
            null
        }
        val url = ServletUtils.getRequest().requestURI
        val ip = ServletUtils.getIpAddr()
        sysLog.url = url
        sysLog.ip = ip
        sysLog.timeCost = timeCost
        return sysLog
    }
}

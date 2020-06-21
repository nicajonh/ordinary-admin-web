package com.llh.server.common.annotation

/**
 * SysLog 系统日志注解
 *
 * CreatedAt: 2020-06-21 15:50
 *
 * @author llh
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SysLogAnnotation(val value: String = "")

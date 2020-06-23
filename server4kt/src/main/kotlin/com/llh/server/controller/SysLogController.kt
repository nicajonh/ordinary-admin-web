package com.llh.server.controller

import com.llh.server.model.SysLog
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.service.sys.SysLogService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysLogController
 *
 * CreatedAt: 2020-06-23 22:06
 *
 * @author llh
 */
@RestController
@RequestMapping("log")
@Api("系统日志信息操作模块。")
class SysLogController {
    @Autowired
    @Qualifier("sysLogService")
    private lateinit var sysLogService: SysLogService

    @PostMapping("list")
    @ApiOperation("获取系统日志列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysLog>): JsonWrapper {
        val page = sysLogService.page(pageQueryVO)
        return okResponse(page)
    }

    @GetMapping("{logId}")
    @ApiOperation("根据系统日志id获取系统日志信息。")
    fun userInfo(@PathVariable("logId") logId: String): JsonWrapper {
        val user = sysLogService.findById(logId)
        return okResponse(user)
    }

    @DeleteMapping("delete/{logId}")
    @ApiOperation("根据系统日志id移除系统日志信息。")
    fun remove(@PathVariable("logId") logId: String): JsonWrapper {
        val remove = sysLogService.remove(logId)
        return okResponse(remove)
    }
}

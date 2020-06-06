package com.llh.server.controller

import com.llh.server.model.SysUser
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.PageQueryInfo
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.service.SysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * SysUserController
 *
 * CreatedAt: 2020-06-06 20:45
 *
 * @author llh
 */
@RestController
@RequestMapping("user")
@Api("用户信息操作模块。")
class SysUserController {

    @Autowired
    @Qualifier("sysUserService")
    private lateinit var sysUserService: SysUserService

    @PostMapping("list")
    @ApiOperation("获取用户列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysUser>): JsonWrapper {
        val page = sysUserService.page(pageQueryVO)
        return okResponse(page)
    }
}

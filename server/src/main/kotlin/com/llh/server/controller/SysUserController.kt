package com.llh.server.controller

import com.llh.server.model.SysUser
import com.llh.server.pojo.*
import com.llh.server.service.SysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

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

    @GetMapping("{userId}")
    @ApiOperation("根据用户id获取用户信息。")
    fun userInfo(@PathVariable("userId") userId: String): JsonWrapper {
        val user = sysUserService.findById(userId)
        return okResponse(user)
    }

    @PostMapping
    @ApiOperation("新增一个用户。")
    fun add(userVO: RegisterOrUpdateVO): JsonWrapper {
        println(userVO)
        return okResponse("")
    }

    @PutMapping("update")
    @ApiOperation("更新用户信息。")
    fun update(@RequestBody userVO: RegisterOrUpdateVO): JsonWrapper {
        println(userVO)
        return okResponse("")
    }
}

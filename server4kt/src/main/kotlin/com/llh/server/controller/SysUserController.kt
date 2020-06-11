package com.llh.server.controller

import com.llh.server.model.SysUser
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.RegisterOrUpdateVO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.service.sys.SysUserService
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

    @DeleteMapping("delete/{userId}")
    @ApiOperation("根据用户id移除用户信息。")
    fun remove(@PathVariable("userId") userId: String): JsonWrapper {
        val remove = sysUserService.remove(userId)
        return okResponse(remove)
    }

    @PostMapping
    @ApiOperation("新增一个用户。")
    fun add(@RequestBody userVO: RegisterOrUpdateVO): JsonWrapper {
        val saved: Boolean = sysUserService.registerUser(userVO)
        return okResponse("")
    }

    @PutMapping("update")
    @ApiOperation("更新用户信息。")
    fun update(@RequestBody userVO: RegisterOrUpdateVO): JsonWrapper {
        println(userVO)
        return okResponse("")
    }
}

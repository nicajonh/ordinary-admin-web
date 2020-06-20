package com.llh.server.controller

import com.llh.server.model.SysRole
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.RoleInfoVO
import com.llh.server.service.sys.SysRoleService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysRoleController
 *
 * CreatedAt: 2020-06-15 21:40
 *
 * @author llh
 */
@RestController
@RequestMapping("role")
@Api("角色信息操作模块。")
class SysRoleController {
    @Autowired
    @Qualifier("sysRoleService")
    private lateinit var sysRoleService: SysRoleService

    @GetMapping("{roleId}")
    @ApiOperation("根据角色id获取角色信息。")
    fun getOneById(@PathVariable("roleId") entityId: String): JsonWrapper {
        val entity = sysRoleService.findById(entityId)
        return okResponse(entity)
    }

    @DeleteMapping("delete/{roleId}")
    @ApiOperation("根据角色id移除角色信息。")
    fun removeById(@PathVariable("roleId") entityId: String): JsonWrapper {
        val removed = sysRoleService.remove(id = entityId)
        return okResponse(removed)
    }

    @PostMapping("list")
    @ApiOperation("获取角色列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysRole>): JsonWrapper {
        val page = sysRoleService.page(pageQueryVO)
        return okResponse(page)
    }

    @PostMapping
    @ApiOperation("新增一个角色信息。")
    fun addModel(@RequestBody roleInfoVO: RoleInfoVO): JsonWrapper {
        val saved: Boolean = sysRoleService.saveByVO(roleInfoVO)
        return okResponse(saved)
    }
}

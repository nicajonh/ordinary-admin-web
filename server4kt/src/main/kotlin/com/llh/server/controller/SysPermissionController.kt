package com.llh.server.controller

import com.llh.server.model.SysPermission
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.PermissionInfoVO
import com.llh.server.service.sys.SysPermissionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysPermissionController
 *
 * CreatedAt: 2020-06-17 22:44
 *
 * @author llh
 */
@RestController
@RequestMapping("permission")
@Api("权限信息操作模块。仅作添加数据使用。按理来说不应该提供给用户使用。")
class SysPermissionController {
    @Autowired
    @Qualifier("sysPermissionService")
    private lateinit var sysPermissionService: SysPermissionService

    @GetMapping("{roleId}")
    @ApiOperation("根据权限id获取权限信息。")
    fun getOneById(@PathVariable("roleId") entityId: String): JsonWrapper {
        val entity = sysPermissionService.findById(entityId)
        return okResponse(entity)
    }

    @DeleteMapping("delete/{roleId}")
    @ApiOperation("根据权限id移除权限信息。")
    fun removeById(@PathVariable("roleId") entityId: String): JsonWrapper {
        val removed = sysPermissionService.remove(id = entityId)
        return okResponse(removed)
    }


    @PostMapping("list")
    @ApiOperation("获取权限列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysPermission>): JsonWrapper {
        val page = sysPermissionService.page(pageQueryVO)
        return okResponse(page)
    }

    @PostMapping
    @ApiOperation("新增一个权限列表。")
    fun addModel(@RequestBody infoVO: PermissionInfoVO): JsonWrapper {
        val saved: Boolean = sysPermissionService.saveByVO(infoVO)
        return okResponse(saved)
    }

    @GetMapping("tree")
    @ApiOperation("获取权限树形结构。")
    fun tree(): JsonWrapper {
        val tree: SysPermission = sysPermissionService.treeData()
        return okResponse(tree)
    }
}

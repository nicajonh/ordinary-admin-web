package com.llh.server.controller

import com.llh.server.model.SysDept
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.DeptInfoVO
import com.llh.server.service.sys.SysDeptService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysDeptController
 *
 * CreatedAt: 2020-06-12 22:43
 *
 * @author llh
 */
@RestController
@RequestMapping("dept")
@Api("部门信息操作模块。")
class SysDeptController {
    @Autowired
    @Qualifier("sysDeptService")
    private lateinit var sysDeptService: SysDeptService

    @PostMapping("list")
    @ApiOperation("获取部门列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysDept>): JsonWrapper {
        val page = sysDeptService.page(pageQueryVO)
        return okResponse(page)
    }

    @GetMapping("{deptId}")
    @ApiOperation("根据部门id获取部门信息。")
    fun getOneById(@PathVariable("deptId") entityId: String): JsonWrapper {
        val entity = sysDeptService.findById(entityId)
        return okResponse(entity)
    }

    @DeleteMapping("delete/{deptId}")
    @ApiOperation("根据部门id移除部门信息。")
    fun removeById(@PathVariable("deptId") entityId: String): JsonWrapper {
        val removed = sysDeptService.remove(id = entityId)
        return okResponse(removed)
    }

    @PostMapping
    @ApiOperation("新增一个部门。")
    fun addEntity(@RequestBody infoVO: DeptInfoVO): JsonWrapper {
        val saved = sysDeptService.saveByVO(infoVO)
        return okResponse(saved)
    }

    @PutMapping("update")
    @ApiOperation("更新部门信息。")
    fun update(@RequestBody infoVO: DeptInfoVO): JsonWrapper {
        val updated: Boolean = sysDeptService.updateByVO(infoVO)
        return okResponse(updated)
    }

    @GetMapping("tree")
    @ApiOperation("获取部门信息。并以树形结构返回。")
    fun treeInfo(): JsonWrapper {
        val tree = sysDeptService.takeTreeInfo()
        return okResponse(tree)
    }
}

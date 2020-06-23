package com.llh.server.controller

import com.llh.server.model.SysDictType
import com.llh.server.pojo.*
import com.llh.server.pojo.vo.DictTypeVO
import com.llh.server.service.sys.SysDictTypeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysDictTypeController
 *
 * CreatedAt: 2020-06-23 22:14
 *
 * @author llh
 */
@RestController
@RequestMapping("dict/type")
@Api("字典类型信息操作模块。")
class SysDictTypeController {
    @Autowired
    @Qualifier("sysDictTypeService")
    private lateinit var sysDictTypeService: SysDictTypeService

    @PostMapping("list")
    @ApiOperation("获取字典类型列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysDictType>): JsonWrapper {
        val page: PageDTO<SysDictType> = sysDictTypeService.page(pageQueryVO)
        return okResponse(page)
    }

    @GetMapping("{dictId}")
    @ApiOperation("根据字典类型id获取字典类型信息。")
    fun userInfo(@PathVariable("dictId") dictId: String): JsonWrapper {
        val user = sysDictTypeService.findById(dictId)
        return okResponse(user)
    }

    @DeleteMapping("delete/{dictId}")
    @ApiOperation("根据字典类型id移除字典类型信息。")
    fun remove(@PathVariable("dictId") dictId: String): JsonWrapper {
        val remove = sysDictTypeService.remove(dictId)
        return okResponse(remove)
    }

    @PostMapping
    @ApiOperation("新增一个字典类型。")
    fun add(@RequestBody dictTypeVO: DictTypeVO): JsonWrapper {
        val saved: Boolean = sysDictTypeService.saveByVO(dictTypeVO)
        return okResponse(saved)
    }

    @PutMapping("update")
    @ApiOperation("更新字典类型信息。")
    fun update(@RequestBody dictTypeVO: DictTypeVO): JsonWrapper {
        val updated: Boolean? = sysDictTypeService.updateByVO(dictTypeVO)
        updated ?: return okResponse(false, msg = "更新字典类型信息失败！")
        return okResponse(updated)
    }

}

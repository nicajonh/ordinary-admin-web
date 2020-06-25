package com.llh.server.controller

import com.llh.server.model.SysDictData
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.DictDataVO
import com.llh.server.pojo.vo.DictTypeVO
import com.llh.server.service.sys.SysDictDataService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

/**
 * SysDictDataController
 *
 * CreatedAt: 2020-06-23 22:57
 *
 * @author llh
 */
@RestController
@RequestMapping("dict/data")
@Api("字典数据信息操作模块。")
class SysDictDataController {
    @Autowired
    @Qualifier("sysDictDataService")
    private lateinit var sysDictDataService: SysDictDataService

    @PostMapping("list")
    @ApiOperation("获取字典数据列表，并以分页的形式返回。")
    fun page(@RequestBody pageQueryVO: SimplePageQueryVO<SysDictData>): JsonWrapper {
        val page: PageDTO<SysDictData> = sysDictDataService.page(pageQueryVO)
        return okResponse(page)
    }

    @GetMapping("{dictId}")
    @ApiOperation("根据字典数据id获取字典数据信息。")
    fun info(@PathVariable("dictId") dictId: String): JsonWrapper {
        val user = sysDictDataService.findById(dictId)
        return okResponse(user)
    }
    @DeleteMapping("delete/{dictId}")
    @ApiOperation("根据字典数据id移除字典数据信息。")
    fun remove(@PathVariable("dictId") dictId: String): JsonWrapper {
        val remove = sysDictDataService.remove(dictId)
        return okResponse(remove)
    }

    @PostMapping
    @ApiOperation("新增一个字典数据。")
    fun add(@RequestBody dictDataVO: DictDataVO): JsonWrapper {
        val saved: Boolean = sysDictDataService.saveByVO(dictDataVO)
        return okResponse(saved)
    }
    @PutMapping("update")
    @ApiOperation("更新字典数据信息。")
    fun update(@RequestBody dictDataVO: DictDataVO): JsonWrapper {
        val updated: Boolean? = sysDictDataService.updateByVO(dictDataVO)
        updated ?: return okResponse(false, msg = "更新字典数据信息失败！")
        return okResponse(updated)
    }
}

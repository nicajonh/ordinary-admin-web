package com.llh.server.service.sys

import com.llh.server.model.SysDictData
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DictDataVO
import com.llh.server.service.BasicService

/**
 * SysDictDataService
 *
 * CreatedAt: 2020-06-23 22:58
 *
 * @author llh
 */
interface SysDictDataService : BasicService<SysDictData> {
    fun page(pageQueryVO: SimplePageQueryVO<SysDictData>): PageDTO<SysDictData>
    fun saveByVO(dictDataVO: DictDataVO): Boolean
    fun updateByVO(dictDataVO: DictDataVO): Boolean?
}

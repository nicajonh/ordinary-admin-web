package com.llh.server.service.sys

import com.llh.server.model.SysDictType
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DictTypeVO
import com.llh.server.service.BasicService

/**
 * SysDictTypeService
 *
 * CreatedAt: 2020-06-23 21:28
 *
 * @author llh
 */
interface SysDictTypeService : BasicService<SysDictType> {
    fun page(pageQueryVO: SimplePageQueryVO<SysDictType>): PageDTO<SysDictType>
    fun saveByVO(dictTypeVO: DictTypeVO): Boolean
    fun updateByVO(dictTypeVO: DictTypeVO): Boolean?
}

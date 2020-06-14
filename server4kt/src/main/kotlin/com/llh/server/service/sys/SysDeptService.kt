package com.llh.server.service.sys

import com.llh.server.model.SysDept
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DeptInfoVO
import com.llh.server.service.BasicService

/**
 * SysDeptService 部门信息服务层
 *
 * CreatedAt: 2020-06-12 22:23
 *
 * @author llh
 */
interface SysDeptService : BasicService<SysDept> {
    /**
     * 简单信息的分页信息
     */
    fun page(queryVO: SimplePageQueryVO<SysDept>): PageDTO<SysDept>

    /**
     * 通过[infoVO]类保存部门信息。
     */
    fun saveByVO(infoVO: DeptInfoVO): SysDept

    /**
     * 获取树结构信息
     */
    fun takeTreeInfo(): SysDept

    /**
     * 更新
     */
    fun updateByVO(infoVO: DeptInfoVO): Boolean

}

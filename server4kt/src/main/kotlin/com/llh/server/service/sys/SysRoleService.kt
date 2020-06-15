package com.llh.server.service.sys

import com.llh.server.model.SysRole
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.service.BasicService

/**
 * SysRoleService
 *
 * CreatedAt: 2020-06-14 15:13
 *
 * @author llh
 */
interface SysRoleService : BasicService<SysRole> {
    fun page(pageQueryVO: SimplePageQueryVO<SysRole>): PageDTO<SysRole>
}

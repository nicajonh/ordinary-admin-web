package com.llh.server.service.sys

import com.llh.server.model.SysPermission
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.PermissionInfoVO
import com.llh.server.service.BasicService

/**
 * SysPermissionService
 *
 * CreatedAt: 2020-06-17 22:55
 *
 * @author llh
 */
interface SysPermissionService : BasicService<SysPermission> {
    fun saveByVO(infoVO: PermissionInfoVO): Boolean
    fun page(pageQueryVO: SimplePageQueryVO<SysPermission>): PageDTO<SysPermission>
    fun treeData(): SysPermission
}

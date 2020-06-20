package com.llh.server.service.sys

import com.llh.server.model.SysRole
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.RoleInfoVO
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

    /**
     * 通过[roleInfoVO]VO类来保存角色信息。
     */
    fun saveByVO(roleInfoVO: RoleInfoVO): Boolean

    /**
     * 通过[infoVO]VO类来更新角色信息及其关联的权限信息。
     */
    fun updateByVO(infoVO: RoleInfoVO): Boolean
}

package com.llh.server.service.sys.impl

import com.llh.server.dao.SysRoles
import com.llh.server.model.SysRole
import com.llh.server.model.copyProperties
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.RoleInfoVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysRoleService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * SysRoleServiceImpl
 *
 * CreatedAt: 2020-06-14 15:14
 *
 * @author llh
 */
@Service("sysRoleService")
class SysRoleServiceImpl : ServiceHelper<SysRole>(), SysRoleService, Logging {
    @Autowired
    private lateinit var database: Database

    override fun save(entity: SysRole): SysRole {
        initValueForModelToDB(entity)
        database.sequenceOf(SysRoles).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysRoles) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysRole): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysRole? {
        val find = database.sequenceOf(SysRoles).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find SysRole(id:${id}) info")
        return find
    }

    override fun saveByVO(roleInfoVO: RoleInfoVO): Boolean {
        val role = SysRole()
        role.roleName = roleInfoVO.roleName
        role.dataScope = roleInfoVO.dataScope
        role.orderNum = roleInfoVO.orderNum
        role.remark = roleInfoVO.remark
        save(role)
        return role.id.isNotEmpty()
    }

    override fun page(pageQueryVO: SimplePageQueryVO<SysRole>): PageDTO<SysRole> {
        var total = 0
        val model = pageQueryVO.model
        val query = database.from(SysRoles)
            .select(SysRoles.columns)
            .whereWithConditions {
                if (!model?.roleName.isNullOrBlank()) {
                    it += SysRoles.roleName like "%${model?.roleName}%"
                }
                it += SysRoles.removeFlag eq persistence
            }.limit(pageQueryVO.pageStartIndex(), pageQueryVO.pageSize)
            .orderBy(SysRoles.orderNum.asc())
            .map { row ->
                total = row.query.totalRecords
                SysRoles.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = pageQueryVO.pageSize
        )
    }
}

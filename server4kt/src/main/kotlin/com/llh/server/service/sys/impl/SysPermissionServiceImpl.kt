package com.llh.server.service.sys.impl

import com.llh.server.dao.SysPermissions
import com.llh.server.model.SysPermission
import com.llh.server.model.copyProperties
import com.llh.server.pojo.vo.PermissionInfoVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysPermissionService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.update
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * SysPermissionService
 *
 * CreatedAt: 2020-06-17 22:56
 *
 * @author llh
 */
@Service("sysPermissionService")
class SysPermissionServiceImpl : ServiceHelper<SysPermission>(), SysPermissionService, Logging {
    @Autowired
    private lateinit var database: Database
    override fun save(entity: SysPermission): SysPermission {
        initValueForModelToDB(entity)
        database.sequenceOf(SysPermissions).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysPermissions) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysPermission): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysPermission? {
        val find = database.sequenceOf(SysPermissions).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find SysPermission(id:${id}) info")
        return find
    }

    override fun saveByVO(infoVO: PermissionInfoVO): Boolean {
        val permission = SysPermission()
        permission.orderNum = infoVO.orderNum
        permission.permName = infoVO.permName
        permission.parentId = infoVO.parentId
        permission.remark = infoVO.remark
        save(permission)
        return permission.id.isNotEmpty()
    }
}

package com.llh.server.service.sys.impl

import com.llh.server.dao.SysRoles
import com.llh.server.model.SysRole
import com.llh.server.model.copyProperties
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysRoleService
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
}

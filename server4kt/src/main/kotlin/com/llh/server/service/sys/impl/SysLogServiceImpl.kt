package com.llh.server.service.sys.impl

import com.llh.server.dao.SysLogs
import com.llh.server.model.SysLog
import com.llh.server.model.copyProperties
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysLogService
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
 * SysLogServiceImpl
 *
 * CreatedAt: 2020-06-21 16:35
 *
 * @author llh
 */
@Service("sysLogService")
class SysLogServiceImpl : ServiceHelper<SysLog>(), SysLogService, Logging {
    @Autowired
    private lateinit var database: Database

    override fun save(entity: SysLog): SysLog {
        initValueForModelToDB(entity)
        database.sequenceOf(SysLogs).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysLogs) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysLog): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysLog? {
        val find = database.sequenceOf(SysLogs).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find SysLogs(id:${id}) info")
        return find
    }
}

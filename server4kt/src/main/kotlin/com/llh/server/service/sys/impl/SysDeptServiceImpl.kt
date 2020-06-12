package com.llh.server.service.sys.impl

import com.llh.server.dao.SysDepts
import com.llh.server.model.SysDept
import com.llh.server.model.copyProperties
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysDeptService
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
 * SysDeptServiceImpl
 *
 * CreatedAt: 2020-06-12 22:24
 *
 * @author llh
 */
@Service("sysDeptService")
class SysDeptServiceImpl : ServiceHelper<SysDept>(), SysDeptService, Logging {
    @Autowired
    private lateinit var database: Database

    override fun save(entity: SysDept): SysDept {
        initValueForModelToDB(entity)
        database.sequenceOf(SysDepts).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysDepts) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysDept): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysDept? {
        val find = database.sequenceOf(SysDepts).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find sys_dept(id:${id}) info")
        return find
    }
}

package com.llh.server.service.sys.impl

import com.llh.server.dao.SysDictDatas
import com.llh.server.dao.SysDictTypes
import com.llh.server.model.SysDictData
import com.llh.server.model.copyProperties
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysDictDataService
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
 * SysDictDataServiceIml
 *
 * CreatedAt: 2020-06-23 22:59
 *
 * @author llh
 */
@Service("sysDictDataService")
class SysDictDataServiceIml : ServiceHelper<SysDictData>(), SysDictDataService, Logging {
    @Autowired
    private lateinit var database: Database

    override fun save(entity: SysDictData): SysDictData {
        initValueForModelToDB(entity)
        database.sequenceOf(SysDictDatas).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysDictDatas) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysDictData): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysDictData? {
        val find = database.sequenceOf(SysDictDatas).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find SysDictTypes(id:${id}) info")
        return find
    }
}

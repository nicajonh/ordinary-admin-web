package com.llh.server.service.sys.impl

import com.llh.server.dao.SysDictTypes
import com.llh.server.model.SysDictType
import com.llh.server.model.copyProperties
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DictTypeVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysDictTypeService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * SysDictTypeServiceImpl
 *
 * CreatedAt: 2020-06-23 21:29
 *
 * @author llh
 */
@Service("sysDictTypeService")
class SysDictTypeServiceImpl : ServiceHelper<SysDictType>(), SysDictTypeService, Logging {
    @Autowired
    private lateinit var database: Database

    override fun save(entity: SysDictType): SysDictType {
        initValueForModelToDB(entity)
        database.sequenceOf(SysDictTypes).add(entity)
        return entity
    }

    override fun saveByVO(dictTypeVO: DictTypeVO): Boolean {
        val entity = SysDictType()
        entity.dictName = dictTypeVO.dictName
        entity.dictType = dictTypeVO.dictType
        entity.internalFlag = dictTypeVO.internalFlag
        entity.remark = dictTypeVO.remark
        save(entity)
        return entity.id.isNotEmpty()
    }

    override fun updateByVO(dictTypeVO: DictTypeVO): Boolean? {
        dictTypeVO.id ?: return false
        val entity = SysDictType()
        entity.dictName = dictTypeVO.dictName
        entity.dictType = dictTypeVO.dictType
        entity.internalFlag = dictTypeVO.internalFlag
        entity.remark = dictTypeVO.remark
        entity.id = dictTypeVO.id
        return updateById(entity)
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysDictTypes) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysDictType): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        model.updatedBy = currentUserId()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysDictType? {
        val find = database.sequenceOf(SysDictTypes).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find SysDictTypes(id:${id}) info")
        return find
    }

    override fun page(pageQueryVO: SimplePageQueryVO<SysDictType>): PageDTO<SysDictType> {
        return pageQuery(pageQueryVO)
    }

    // ----------------------- private fun --------------------------

    private fun pageQuery(pageQueryVO: SimplePageQueryVO<SysDictType>): PageDTO<SysDictType> {
        var total = 0
        val query = database.from(SysDictTypes)
            .select(SysDictTypes.columns)
            .whereWithConditions {
                if (pageQueryVO.model?.dictName?.isNotBlank() == true) {
                    it += SysDictTypes.dictName like "%${pageQueryVO.model.dictName}%"
                }
                it += SysDictTypes.removeFlag eq persistence
            }.limit(pageQueryVO.pageStartIndex(), pageQueryVO.pageSize)
            .orderBy(orderCondition(SysDictTypes,pageQueryVO))
            .map { row ->
                total = row.query.totalRecords
                SysDictTypes.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = pageQueryVO.pageSize
        )
    }
}

package com.llh.server.service.sys.impl

import com.llh.server.dao.SysDictDatas
import com.llh.server.model.SysDictData
import com.llh.server.model.copyProperties
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DictDataVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysDictDataService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
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
            logger.warn("not find SysDictData(id:${id}) info")
        return find
    }

    override fun saveByVO(dictDataVO: DictDataVO): Boolean {
        val entity = SysDictData()
        entity.dictLabel = dictDataVO.dictLabel
        entity.dictType = dictDataVO.dictType
        entity.orderNum = dictDataVO.orderNum
        entity.remark = dictDataVO.remark
        entity.dictValue = dictDataVO.dictValue
        save(entity)
        return entity.id.isNotEmpty()
    }

    override fun updateByVO(dictDataVO: DictDataVO): Boolean? {
        dictDataVO.id ?: return false
        val entity = SysDictData()
        entity.dictLabel = dictDataVO.dictLabel
        entity.dictType = dictDataVO.dictType
        entity.orderNum = dictDataVO.orderNum
        entity.remark = dictDataVO.remark
        entity.dictValue = dictDataVO.dictValue
        entity.id = dictDataVO.id
        return updateById(entity)
    }

    override fun page(pageQueryVO: SimplePageQueryVO<SysDictData>): PageDTO<SysDictData> {
        return pageQuery(pageQueryVO)
    }

    // ----------------------- private fun --------------------------
    private fun pageQuery(pageQueryVO: SimplePageQueryVO<SysDictData>): PageDTO<SysDictData> {
        var total = 0
        val model = pageQueryVO.model
        val query = database.from(SysDictDatas)
            .select(SysDictDatas.columns)
            .whereWithConditions {
                if (model?.dictLabel?.isNotBlank() == true) {
                    it += SysDictDatas.dictLabel like "%${model.dictLabel}%"
                }
                if (model?.dictType?.isNotBlank() == true) {
                    it += SysDictDatas.dictType eq model.dictType
                }
                it += SysDictDatas.removeFlag eq persistence
            }.limit(pageQueryVO.pageStartIndex(), pageQueryVO.pageSize)
            .orderBy(orderCondition(SysDictDatas, pageQueryVO))
            .map { row ->
                total = row.query.totalRecords
                SysDictDatas.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = pageQueryVO.pageSize
        )
    }
}

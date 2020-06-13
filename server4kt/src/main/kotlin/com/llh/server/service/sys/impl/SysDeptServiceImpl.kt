package com.llh.server.service.sys.impl

import com.llh.server.dao.SysDepts
import com.llh.server.model.SysDept
import com.llh.server.model.copyProperties
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.DeptInfoVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysDeptService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
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

    override fun takeTreeInfo(): SysDept {
        val list = database.from(SysDepts)
            .select(SysDepts.columns)
            .where { SysDepts.removeFlag eq persistence }
            .orderBy(SysDepts.parentId.asc(), SysDepts.orderNum.asc()) // 这里的排序条件对生成树形结构数据至关重要！
            .map { row -> SysDepts.createEntity(row) }
        return genTreeData(list)
    }

    private fun genTreeData(list: List<SysDept>): SysDept {
        val root = SysDept()
        root.deptName = "部门树" // 暂时先写死吧
        root.id = ""
        val treeMap = mutableMapOf<String?, SysDept>()
        treeMap[null] = root
        for (dept in list) {
            if (dept.parentId.isNullOrBlank()) {
                treeMap[null]?.children?.add(dept)
                treeMap[dept.id] = dept
                continue
            }
            if (treeMap.containsKey(dept.parentId)) {
                treeMap[dept.parentId]?.children?.add(dept)
            }
            treeMap[dept.id] = dept
        }
        return root
    }

    override fun page(queryVO: SimplePageQueryVO<SysDept>): PageDTO<SysDept> {
        return pageQuery(queryVO)
    }

    override fun saveByVO(infoVO: DeptInfoVO): SysDept {
        val dept = SysDept()
        dept.deptName = infoVO.deptName
        if (!infoVO.parentId.isNullOrBlank()) {
            dept.parentId = infoVO.parentId
        }
        dept.orderNum = infoVO.orderNum
        return save(dept)
    }

    private fun pageQuery(queryVO: SimplePageQueryVO<SysDept>): PageDTO<SysDept> {
        var total = 0
        val query = database.from(SysDepts)
            .select(SysDepts.columns)
            .whereWithConditions {
                if (queryVO.model?.deptName?.isNotBlank() == true) {
                    it += SysDepts.deptName like "%${queryVO.model.deptName}%"
                }
                it += SysDepts.removeFlag eq persistence
            }.limit(queryVO.pageStartIndex(), queryVO.pageSize)
            .orderBy(SysDepts.updatedAt.desc())
            .map { row ->
                total = row.query.totalRecords
                SysDepts.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = queryVO.pageSize
        )
    }
}

package com.llh.server.service.sys.impl

import com.llh.server.dao.SysPermissions
import com.llh.server.model.SysPermission
import com.llh.server.model.copyProperties
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.PermissionInfoVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysPermissionService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
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

    override fun page(pageQueryVO: SimplePageQueryVO<SysPermission>): PageDTO<SysPermission> {
        var total = 0
        val model = pageQueryVO.model
        val query = database.from(SysPermissions)
            .select(SysPermissions.columns)
            .whereWithConditions {
                if (!model?.permName.isNullOrBlank()) {
                    it += SysPermissions.permName like "%${model?.permName}%"
                }
                it += SysPermissions.removeFlag eq persistence
            }.limit(pageQueryVO.pageStartIndex(), pageQueryVO.pageSize)
            .orderBy(SysPermissions.orderNum.asc())
            .map { row ->
                total = row.query.totalRecords
                SysPermissions.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = pageQueryVO.pageSize
        )
    }

    override fun treeData(): SysPermission {
        val list = database.from(SysPermissions)
            .select(SysPermissions.columns)
            .where { SysPermissions.removeFlag eq persistence }
            .map { row -> SysPermissions.createEntity(row) }
        return genTreeData(list)
    }

    // ----------------- private fun  ------------------

    private fun genTreeData(list: List<SysPermission>): SysPermission {
        val root = SysPermission()
        root.permName = "权限树"
        root.id = ""
        val treeMap = mutableMapOf<String?, SysPermission>()
        treeMap[null] = root
        for (ele in list) {
            if (treeMap.containsKey(ele.parentId)) {
                treeMap[ele.parentId]?.children?.add(ele)
            }
            treeMap[ele.id] = ele
        }
        return root
    }
}

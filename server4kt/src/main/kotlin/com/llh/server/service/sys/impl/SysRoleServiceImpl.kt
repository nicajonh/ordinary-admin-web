package com.llh.server.service.sys.impl

import com.llh.server.common.util.uuidStr
import com.llh.server.dao.SysRoles
import com.llh.server.dao.relation.RolePermRelations
import com.llh.server.model.SysRole
import com.llh.server.model.copyProperties
import com.llh.server.model.relation.RolePermRelation
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.vo.RoleInfoVO
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysPermissionService
import com.llh.server.service.sys.SysRoleService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.removeIf
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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

    @Autowired
    @Qualifier("sysPermissionService")
    private lateinit var sysPermissionService: SysPermissionService

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
        // 删除已存在的关系
        val deleted = database.delete(RolePermRelations) {
            it.roleId eq id
        }
        logger.debug("删除角色（$id） $deleted 条权限信息。")
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
        val role = SysRole().convert2Entity(roleInfoVO)
        save(role)
        val saved = saveRolePermRelation(role.id, roleInfoVO.permIds)
        logger.debug("saved ? $saved")
        return role.id.isNotEmpty()
    }

    override fun updateByVO(infoVO: RoleInfoVO): Boolean {
        val entity = SysRole().convert2Entity(infoVO)
        updateById(entity)
        updateRolePermRelation(entity.id, infoVO.permIds)
        return entity.id.isNotBlank()
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

    // ------------------------- private fun -----------------------------------

    /**
     * 保存角色与权限之间的关系
     */
    private fun saveRolePermRelation(roleId: String, permIds: MutableSet<String>?): Boolean {
        if (permIds.isNullOrEmpty()) {
            logger.debug("新增角色(id:$roleId)时，未设定其拥有的权限。")
            return false
        }
        return addRolePermRelation(permIds, roleId)
    }


    /**
     * 更新角色与权限之间的关系
     */
    private fun updateRolePermRelation(roleId: String, permIds: MutableSet<String>?): Boolean {
        if (permIds.isNullOrEmpty()) {
            // 删除已存在的关系
            val deleted = database.delete(RolePermRelations) {
                it.roleId eq roleId
            }
            logger.info("传入权限关系为空，将删除角色（$roleId）相关的所有权限关系。已删除关系数据 $deleted 条。")
            return deleted > 0

        }

        // 查找已存在的关系
        val savedRelation = database.from(RolePermRelations)
            .select(RolePermRelations.id)
            .where { RolePermRelations.roleId eq roleId }
            .map { row -> RolePermRelations.createEntity(row) }

        val deletedIds = savedRelation.map { it.permId }
        if (deletedIds.containsAll(permIds)) {
            logger.info("角色（$roleId）已保存和传入的权限关系相同。")
            return false
        }
        // 删除已存在的关系
        val deleted = database.delete(RolePermRelations) {
            it.permId inList deletedIds
            it.roleId eq roleId
        }
        logger.debug("角色（$roleId） 已删除 $deleted 条关系数据。")
        // 保存新的关系
        return addRolePermRelation(permIds, roleId)
    }

    private fun addRolePermRelation(permIds: MutableSet<String>, roleId: String): Boolean {
        val toSavedList = mutableListOf<RolePermRelation>()
        for (id in permIds) { // 这里不能用forEach。uuidStr()生成id速度好像跟不上。
            if (id.isEmpty()) continue
            val rp = RolePermRelation()
            rp.id = uuidStr()
            rp.permId = id
            rp.roleId = roleId
            toSavedList.add(rp)
        }
        return batchInsert4RolePerm(toSavedList) > 0
    }

    /**
     * 处理角色与权限的关联表。
     */
    private fun batchInsert4RolePerm(rolePermList: MutableList<RolePermRelation>): Int {
        if (rolePermList.isEmpty()) return 0
        // 单条插入。
        if (rolePermList.size == 1) {
            return database.sequenceOf(RolePermRelations).add(rolePermList[0])
        }
        //批量插入。参考：https://ktorm.liuwj.me/zh-cn/dml.html#%E6%8F%92%E5%85%A5
        val inserted = database.batchInsert(RolePermRelations) {
            for (rp in rolePermList) {
                item {
                    it.id to rp.id
                    it.roleId to rp.roleId
                    it.permId to rp.permId
                }
            }
        }
        val totalInserted = inserted.sum()
        if (totalInserted < rolePermList.size)
            logger.warn("保存角色关联权限信息数量小于传入数量。" +
                "已保存：$totalInserted 条，应当保存：${rolePermList.size} 条。" +
                "插入详情结果：$inserted")
        return totalInserted
    }
}

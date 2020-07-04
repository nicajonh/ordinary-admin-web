package com.llh.server.service.sys.impl

import com.llh.server.common.util.uuidStr
import com.llh.server.dao.SysUsers
import com.llh.server.dao.relation.UserRoleRelations
import com.llh.server.model.SysUser
import com.llh.server.model.copyProperties
import com.llh.server.model.relation.UserRoleRelation
import com.llh.server.pojo.*
import com.llh.server.service.ServiceHelper
import com.llh.server.service.sys.SysUserService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * SysUserServiceImpl
 *
 * CreatedAt: 2020-06-01 23:30
 *
 * @author llh
 */
@Service("sysUserService")
class SysUserServiceImpl : ServiceHelper<SysUser>(), SysUserService, Logging {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    override fun save(entity: SysUser): SysUser {
        initValueForModelToDB(entity)
        entity.accountStatus = activation
        database.sequenceOf(SysUsers).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysUsers) {
            it.removeFlag to remove
            it.updatedAt to getNow()
            where {
                it.id eq id
            }
        }
        return updated > 0
    }

    override fun updateById(entity: SysUser): Boolean {
        val model = findById(entity.id) ?: return false
        model.copyProperties(entity)
        model.updatedAt = getNow()
        return model.flushChanges() > 0
    }

    override fun findById(id: String): SysUser? {
        val find = database.sequenceOf(SysUsers).find {
            it.id eq id and (it.removeFlag eq persistence)
        }
        if (find == null)
            logger.warn("not find user(id:${id}) info")
        return find
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: return createEmptyAccount()
        return convertAccount(findTopByUsername(username))
    }

    override fun findTopByUsername(username: String): SysUser? {
        return database.sequenceOf(SysUsers)
            .find {
                it.username eq username and (it.removeFlag eq persistence)
            }
    }

    override fun page(queryVO: SimplePageQueryVO<SysUser>): PageDTO<SysUser> {
        return pageQuery(queryVO)
    }

    override fun registerUser(userVO: RegisterOrUpdateVO): Boolean {
        val user = SysUser {
            id = uuidStr()
            username = userVO.username
            password = passwordEncoder.encode(userVO.password)
            email = userVO.email
        }
        save(user)
        saveUserRoleRelation(user.id, userVO.roleIds)
        return user.id.isNotEmpty()
    }

    override fun updateUser(userVO: RegisterOrUpdateVO): Boolean? {
        userVO.id ?: return false
        val user = findById(userVO.id)
        if (userVO.newPassword?.isNotBlank() == true) {
            user?.password = passwordEncoder.encode(userVO.newPassword)
        }
        if (userVO.username != user?.username) {
            user?.username = userVO.username
        }
        if (user?.email?.isNotBlank() == true && userVO.email != user.email) {
            user.email = userVO.email
        }
        val changes = user?.flushChanges()
        updateUserRoleRelation(userVO.id, userVO.roleIds)
        return changes?.equals(1)
    }

    // ----------------------- private fun --------------------------

    private fun pageQuery(queryVO: SimplePageQueryVO<SysUser>): PageDTO<SysUser> {

        var total = 0
        val query = database.from(SysUsers)
            .select(SysUsers.columns)
            .whereWithConditions {
                if (queryVO.model?.username?.isNotBlank() == true) {
                    it += SysUsers.username like "%${queryVO.model.username}%"
                }
                it += SysUsers.removeFlag eq persistence
            }.limit(queryVO.pageStartIndex(), queryVO.pageSize)
            .orderBy(orderCondition(SysUsers, queryVO))
            .map { row ->
                total = row.query.totalRecords
                SysUsers.createEntity(row)
            }
        return PageDTO(
            content = query,
            totalElements = total,
            pageSize = queryVO.pageSize
        )
    }

    /**
     * 将用户类转换为帐户类。
     * 默认返回空的帐户类。
     */
    private fun convertAccount(user: SysUser?): AccountVO {
        user ?: return createEmptyAccount()
        return AccountVO(user.username, user.password, user.id)
    }

    private fun saveUserRoleRelation(userId: String, roleIds: MutableSet<String>?): Boolean {
        if (roleIds.isNullOrEmpty()) {
            logger.debug("新增用户(id:$userId)时，未设定其拥有的角色。")
            return false
        }
        return addUserRoleRelation(userId, roleIds)
    }

    private fun updateUserRoleRelation(userId: String, roleIds: MutableSet<String>?): Boolean {
        if (roleIds.isNullOrEmpty()) {
            val deleted = database.delete(UserRoleRelations) {
                it.userId eq userId
            }
            logger.info("传入角色关系为空，将删除用户（$userId）相关的所有角色关系。已删除关系数据 $deleted 条。")
            return deleted > 0
        }
        // 查找已存在的关系
        val saveRelation = database.from(UserRoleRelations)
            .select(UserRoleRelations.columns)
            .where { UserRoleRelations.userId eq userId }
            .map { row -> UserRoleRelations.createEntity(row) }
        val deletedIds = saveRelation.map { it.roleId }
        if (deletedIds.containsAll(roleIds)) {
            logger.info("用户（$userId）已保存和传入的权限关系相同。")
            return false
        }
        // 删除已存在的关系
        val deleted = database.delete(UserRoleRelations) {
            it.roleId inList roleIds
            it.userId eq userId
        }
        logger.debug("用户（$userId） 已删除 $deleted 条角色关系数据。")
        // 保存新的关系
        return addUserRoleRelation(userId, roleIds)
    }

    private fun addUserRoleRelation(userId: String, roleIds: MutableSet<String>): Boolean {
        val toSaveList = mutableListOf<UserRoleRelation>()
        roleIds.forEach {
            val ur = UserRoleRelation()
            ur.id = uuidStr()
            ur.roleId = it
            ur.userId = userId
            toSaveList.add(ur)
        }
        return batchInsert4UserRole(toSaveList) > 0
    }

    /**
     * 处理用户与角色的关联表。
     */
    private fun batchInsert4UserRole(userRoleList: MutableList<UserRoleRelation>): Int {
        if (userRoleList.isEmpty()) return 0
        if (userRoleList.size == 1) {
            return database.sequenceOf(UserRoleRelations)
                .add(userRoleList[0])
        }
        val inserted = database.batchInsert(UserRoleRelations) {
            for (r in userRoleList) {
                item {
                    it.id to r.id
                    it.roleId to r.roleId
                    it.userId to r.userId
                }
            }
        }
        val totalInserted = inserted.sum()
        if (totalInserted < userRoleList.size)
            logger.warn("保存用户关联角色信息数量小于传入数量。" +
                "已保存：$totalInserted 条，应当保存：${userRoleList.size} 条。" +
                "插入详情结果：$inserted")
        return totalInserted
    }
}

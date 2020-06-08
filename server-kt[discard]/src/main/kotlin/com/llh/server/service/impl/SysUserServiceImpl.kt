package com.llh.server.service.impl

import cn.hutool.core.util.StrUtil
import com.llh.server.dao.SysUsers
import com.llh.server.model.SysUser
import com.llh.server.model.copyProperties
import com.llh.server.pojo.AccountVO
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.pojo.createEmptyAccount
import com.llh.server.service.SysUserService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import me.liuwj.ktorm.schema.ColumnDeclaring
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

/**
 * SysUserServiceImpl
 *
 * CreatedAt: 2020-06-01 23:30
 *
 * @author llh
 */
@Service("sysUserService")
class SysUserServiceImpl : SysUserService, Logging {

    @Autowired
    private lateinit var database: Database


    override fun save(entity: SysUser): SysUser {
        entity.createdAt = getNow()
        entity.updatedAt = getNow()
        entity.accountStatus = activation
        entity.dataStatus = persistence
        database.sequenceOf(SysUsers).add(entity)
        return entity
    }

    override fun remove(id: String): Boolean {
        val updated = database.update(SysUsers) {
            it.dataStatus to remove
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
            it.id eq id and (it.dataStatus eq persistence)
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
                it.username eq username and (it.dataStatus eq true)
            }
    }

    override fun page(queryVO: SimplePageQueryVO<SysUser>): PageDTO<SysUser> {
        return pageQuery(queryVO)
    }

    private fun pageQuery(queryVO: SimplePageQueryVO<SysUser>): PageDTO<SysUser> {
        var total = 0
        val query = database.from(SysUsers)
            .select(SysUsers.columns)
            .whereWithConditions {
                if (StrUtil.isNotBlank(queryVO.model?.username)) {
                    it += SysUsers.username like "%${queryVO.model!!.username}%"
                }
                it += SysUsers.dataStatus eq persistence
            }.limit(queryVO.pageStartIndex(), queryVO.pageSize)
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
}

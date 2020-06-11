package com.llh.server.service.sys.impl

import cn.hutool.core.util.StrUtil
import com.llh.server.common.util.uuidStr
import com.llh.server.dao.SysUsers
import com.llh.server.model.SysUser
import com.llh.server.model.copyProperties
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
class SysUserServiceImpl : ServiceHelper(), SysUserService, Logging {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


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

    override fun registerUser(userVO: RegisterOrUpdateVO): Boolean {
        val user = SysUser {
            id = uuidStr()
            username = userVO.username
            password = passwordEncoder.encode(userVO.password)
            email = userVO.email
        }
        save(user)
        return user.id == ""
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
            .orderBy(SysUsers.updatedAt.desc())
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

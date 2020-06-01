package com.llh.server.service.impl

import com.llh.server.dao.SysUsers
import com.llh.server.model.SysUser
import com.llh.server.service.SysUserService
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.update
import me.liuwj.ktorm.entity.add
import me.liuwj.ktorm.entity.find
import me.liuwj.ktorm.entity.sequenceOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * SysUserServiceImpl
 *
 * CreatedAt: 2020-06-01 23:30
 *
 * @author llh
 */
@Service("sysUserService")
class SysUserServiceImpl : SysUserService {

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
        TODO("not implemented")
    }

    override fun findById(id: String): SysUser? {
        return database.sequenceOf(SysUsers).find {
            it.id eq id
        }
    }

    override fun findTopByUsername(username: String): SysUser? {
        return database.sequenceOf(SysUsers)
            .find {
                it.username eq username and (it.dataStatus eq true)
            }
    }


}

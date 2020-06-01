package com.llh.server.model

import me.liuwj.ktorm.entity.Entity
import java.time.LocalDateTime

/**
 * SysUser
 *
 * CreatedAt: 2020-06-01 22:25
 * https://github.com/vincentlauvlwj/Ktorm/issues/113#issuecomment-598785235
 * @author llh
 */
interface BaseEntity<E : BaseEntity<E>> : Entity<E> {
    companion object : Entity.Factory<SysUser>()
    var id: String
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
    var dataStatus: Boolean
    var updatedBy: String?
    var createdBy: String?
}

interface SysUser : BaseEntity<SysUser> {
    companion object : Entity.Factory<SysUser>()

    var username: String
    var password: String
    var email: String

    /**
     * 修改密码时用。不与数据库的字段映射。
     */
    var newPassword: String
    var accountStatus: Int
}

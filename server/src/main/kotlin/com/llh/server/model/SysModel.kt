package com.llh.server.model

import me.liuwj.ktorm.entity.Entity
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

/**
 * BaseEntity 基础实体类。
 *
 * CreatedAt: 2020-06-01 22:25
 *
 * 写法源自：https://github.com/vincentlauvlwj/Ktorm/issues/113#issuecomment-598785235
 * @author llh
 */
interface BaseEntity<E : BaseEntity<E>> : Entity<E> {
    var id: String
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
    var dataStatus: Boolean
    var updatedBy: String?
    var createdBy: String?
}

/**
 * 默认不拷贝的属性列表。防止数据被破坏。
 * 只读集合。
 */
val notCopyProperties = mutableSetOf("createdAt", "createdBy", "updatedBy", "password")

/**
 * 从另一个实体类中拷贝属性。
 * 通常用于更新数据库的部分字段。
 * [source] 是拷贝源。用它的值更新调用者对应的属性值。
 * [notCopy] 某些字段不被拷贝。默认不拷贝见 [notCopyProperties]
 * 这个方法还不太完善。先用用看吧。
 */
fun <E : BaseEntity<E>> BaseEntity<E>.copyProperties(source: E, notCopy: MutableSet<String> = notCopyProperties) {
    if (this == source) return
    for ((k, v) in source.properties) {
        if (notCopy.contains(k)) continue
        if (null == v) continue
        if (k == "id") {
            if (this[k] != null) continue
        }
        this[k] = v
    }
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


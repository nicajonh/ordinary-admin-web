package com.llh.server.model

import com.llh.server.common.util.uuidStr
import me.liuwj.ktorm.entity.Entity
import java.time.LocalDateTime

/**
 * BasicModel
 *
 * CreatedAt: 2020-06-11 21:26
 *
 * @author llh
 */
/**
 * BaseModel 基础实体类
 *
 * CreatedAt: 2020-06-08 21:40
 *写法源自：https://github.com/vincentlauvlwj/Ktorm/issues/113#issuecomment-598785235
 * @author llh
 */
interface BasicModel<E : BasicModel<E>> : Entity<E> {
    var id: String
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
    var removeFlag: Boolean
    var updatedBy: String?
    var createdBy: String?
}

/**
 * 默认不拷贝的属性列表。防止数据被破坏。
 * 只读集合。
 */
val NOT_COPY_PROPERTIES = setOf(
    "createdAt", "createdBy", "updatedBy", "password")

/**
 * 从另一个实体类中拷贝属性。
 * 通常用于更新数据库的部分字段。
 * [source] 是拷贝源。用它的值更新调用者对应的属性值。
 * [notCopy] 某些字段不被拷贝。默认不拷贝见 [NOT_COPY_PROPERTIES]
 * 这个方法还不太完善。先用用看吧。
 */
fun <E : BasicModel<E>> BasicModel<E>.copyProperties(
    source: E, notCopy: Set<String> = NOT_COPY_PROPERTIES) {
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

/**
 * 使用`data class` 作为数据表映射类时提取的公共属性
 */
open class BasicModelDSL {
    var id: String = uuidStr()
    var createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()
    var removeFlag: Boolean = true
    var updatedBy: String? = null
    var createdBy: String? = null
}

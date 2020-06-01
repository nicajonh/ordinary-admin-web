package com.llh.server.service

import com.llh.server.common.constant.StatusConstant
import java.time.LocalDateTime

/**
 * BasicService 基本服务层。定义了基本的单表操作。
 *
 * CreatedAt: 2020-06-02 00:06
 *
 * @author llh
 */
interface BasicService<E> {

    fun save(entity: E): E

    fun remove(id: String): Boolean

    fun updateById(entity: E): Boolean

    fun findById(id: String): E?

    fun getNow(): LocalDateTime = LocalDateTime.now()

    val remove: Boolean
        get() = StatusConstant.REMOVE.status
    val persistence: Boolean
        get() = StatusConstant.PERSISTENCE.status

}

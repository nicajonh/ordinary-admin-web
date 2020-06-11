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

    /**
     * 保存
     */
    fun save(entity: E): E

    /**
     * 移除
     */
    fun remove(id: String): Boolean

    /**
     * 根据id更新信息 entity.id属性不能为空
     */
    fun updateById(entity: E): Boolean

    /**
     * 根据id查找信息。可能找不到。
     */
    fun findById(id: String): E?


}

/**
 * 服务层辅助类。
 * 提供服务层公共方法。
 * 避免单继承限制使用接口。
 */
open class ServiceHelper {
    fun getNow(): LocalDateTime = LocalDateTime.now()

    val remove: Boolean
        get() = StatusConstant.REMOVE.status
    val persistence: Boolean
        get() = StatusConstant.PERSISTENCE.status
}

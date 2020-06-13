package com.llh.server.controller

import com.llh.server.model.BasicModel
import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.SimplePageQueryVO

/**
 * BasicRestfulFun 请求的基本方法。
 * 统一写法。方便生成代码。
 *
 * CreatedAt: 2020-06-12 23:02
 *
 * @author llh
 */
@Deprecated("好像并不提取公共的方法体。先留着，以后再决定是否删除。")
interface BasicRestfulFun<E : BasicModel<E>> {

    fun page(pageQueryVO: SimplePageQueryVO<E>): JsonWrapper

    fun getOneById(entityId: String): JsonWrapper

    fun removeById(entityId: String): JsonWrapper

    fun addEntity(entity: E): JsonWrapper
}

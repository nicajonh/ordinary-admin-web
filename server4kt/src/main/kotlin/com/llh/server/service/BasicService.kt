package com.llh.server.service

import com.llh.server.common.constant.OrderDirection
import com.llh.server.common.constant.StatusConstant
import com.llh.server.common.util.uuidStr
import com.llh.server.dao.BasicDao
import com.llh.server.model.BasicModel
import com.llh.server.pojo.AccountVO
import com.llh.server.pojo.SimplePageQueryVO
import me.liuwj.ktorm.dsl.asc
import me.liuwj.ktorm.dsl.desc
import me.liuwj.ktorm.dsl.isNull
import me.liuwj.ktorm.expression.OrderByExpression
import org.springframework.security.core.context.SecurityContextHolder
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
 */
open class ServiceHelper<E : BasicModel<E>> {
    fun getNow(): LocalDateTime = LocalDateTime.now()

    val remove: Boolean
        get() = StatusConstant.REMOVE.status
    val persistence: Boolean
        get() = StatusConstant.PERSISTENCE.status

    /**
     * 给要保存到数据库的[model]初始化部分值
     */
    fun initValueForModelToDB(model: E) {
        model.createdAt = getNow()
        model.updatedAt = getNow()
        model.removeFlag = persistence
        model.createdBy = currentUserId()
        model.id = uuidStr()
    }

    fun currentUser(): AccountVO? {
        val authentication = SecurityContextHolder.getContext().authentication ?: return null
        return authentication.principal as AccountVO
    }

    fun currentUserId(): String? {
        return currentUser()?.id
    }

    /**
     * 单一排序条件生成函数。
     *
     * 传入不合法字段时会使用`updatedAt`进行排序
     */
    fun orderCondition(dao: BasicDao<E>, queryVO: SimplePageQueryVO<E>): OrderByExpression {
        val orderField = queryVO.orderField
        val orderDirection = queryVO.orderType.toLowerCase()
        return try {
            val col = dao.columns.first {
                it.name == orderField
            } // 应该不会有相同字段名的情况吧
            if (orderDirection == OrderDirection.ASC.direction)
                col.asc()
            else col.desc()
        } catch (e: Exception) { // 避免传入不合法的字段
            dao.updatedAt.asc()
        }
    }
}

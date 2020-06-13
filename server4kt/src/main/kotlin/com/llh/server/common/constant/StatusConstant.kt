/**
 * StatusConstant 状态常量
 *
 * CreatedAt: 2020-06-01 23:34
 *
 * @author llh
 */
package com.llh.server.common.constant


enum class StatusConstant(val status: Boolean) {
    PERSISTENCE(false), REMOVE(true)
}

enum class AccountStatus {
    /**
     * 未激活
     */
    INACTIVE,

    /**
     * 激活（正常使用）
     */
    ACTIVATION,

    /**
     * 锁定
     */
    LOCKED,

    /**
     * 删除
     */
    REMOVED
}

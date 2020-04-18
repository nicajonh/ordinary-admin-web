package com.llh.webserver.common.constant;

/**
 * AccountStatus
 * <p>
 * CreatedAt: 2020-04-18 16:58
 *
 * @author llh
 */
public enum AccountStatus {
    /**
     * 未激活
     */
    INACTIVE,
    /**
     * 激活（正常使用）
     */
    ACTIVE,
    /**
     * 锁定
     */
    LOCKED,
    /**
     * 删除
     */
    REMOVED;
}

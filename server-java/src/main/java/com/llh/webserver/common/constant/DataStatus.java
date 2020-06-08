package com.llh.webserver.common.constant;

/**
 * DataStatus 数据状态常量
 * <p>
 * CreatedAt: 2020-04-18 16:42
 *
 * @author llh
 */
public interface DataStatus {
    /**
     * 数据正常状态。
     */
    int PERSISTENCE = 1;
    /**
     * 数据被移除状态。
     */
    int REMOVE = 0;
}

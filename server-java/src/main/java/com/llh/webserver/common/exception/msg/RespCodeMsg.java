package com.llh.webserver.common.exception.msg;

/**
 * RespCodeMsg
 * <p>
 * CreatedAt: 2020-04-18 17:22
 *
 * @author llh
 */
public interface RespCodeMsg {

    /**
     * 系统正常响应的代码
     */
    Integer SUCCESS_CODE = 200_000;
    /**
     * 系统异常代码。
     * 不知道是哪个模块的就用这个吧。
     */
    Integer SYSTEM_ERROR_CODE = 300_000;
    /**
     * 授权异常代码。
     */
    Integer AUTH_ERROR_CODE = 401_000;
    /**
     * 数据异常
     */
    Integer DATA_ERROR_CODE = 510_000;

    Integer getCode();

    String getMsg();
}

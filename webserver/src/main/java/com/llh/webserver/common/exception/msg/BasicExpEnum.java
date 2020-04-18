package com.llh.webserver.common.exception.msg;

/**
 * BasicExpEnum
 * <p>
 * CreatedAt: 2020-04-18 17:21
 *
 * @author llh
 */
public enum BasicExpEnum implements RespCodeMsg {
    SUCCESS(SUCCESS_CODE, "操作成功"),
    SYSTEM_ERROR(SUCCESS_CODE, "系统异常"),

    // 数据异常
    DATA_ERROR(DATA_ERROR_CODE, "数据异常"),
    DATA_DUPLICATION_ERROR(DATA_ERROR_CODE + 1, "数据重复异常"),
    DATA_NONENTITY_ERROR(DATA_ERROR_CODE + 2, "数据不存在"),
    DATA_VALIDATE_ERROR(DATA_ERROR_CODE + 3, "数据验证异常"),
    JSON_CONVERT_EXCEPTION(DATA_ERROR_CODE + 4, "转换JSON数据出错"),
    TOKEN_PARSE_EXCEPTION(DATA_ERROR_CODE + 5, "TOKEN解析异常"),
    TOKEN_NO_USERNAME_EXCEPTION(DATA_ERROR_CODE + 6, "TOKEN中不包含username信息"),
    TOKEN_NO_USER_ID_EXCEPTION(DATA_ERROR_CODE + 7, "TOKEN中不包含user-id信息"),

    // 授权异常
    Auth_ERROR(AUTH_ERROR_CODE, "用户名或密码异常"),
    AUTH_PASSWORD_ERROR(AUTH_ERROR_CODE + 1, "密码异常"),
    AUTH_ACCOUNT_ERROR(AUTH_ERROR_CODE + 2, "帐户异常"),
    AUTH_DENIED_ERROR(AUTH_ERROR_CODE + 3, "权限不足"),
    AUTH_NOTLOGIN_ERROR(AUTH_ERROR_CODE + 3, "未登录"),
    ;

    private Integer code;

    private String msg;

    BasicExpEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}

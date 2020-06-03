/**
 * SysExpInfo
 *
 * CreatedAt: 2020-06-03 21:31
 *
 * @author llh
 */
package com.llh.server.common.constant


/**
 * 系统正常响应的代码
 */
const val SUCCESS_CODE = 200000

/**
 * 系统异常代码。
 * 不知道是哪个模块的就用这个吧。
 */
const val SYSTEM_ERROR_CODE = 300000

/**
 * 授权异常代码。
 */
const val AUTH_ERROR_CODE = 401_000

/**
 * 数据异常
 */
const val DATA_ERROR_CODE = 510_000

/**
 * 空接口。
 * 主要为了给枚举类作标记的，以后传参数方便。
 */
interface SysExpInfo {

}

enum class BasicExp(code: Int, msg: String) : SysExpInfo {
    SUCCESS(SUCCESS_CODE, "操作成功"),

    SYSTEM_ERROR(SYSTEM_ERROR_CODE, "系统异常"),

    DATA_ERROR(DATA_ERROR_CODE, "数据异常"),

    // 授权异常
    Auth_ERROR(AUTH_ERROR_CODE, "用户名或密码异常"), ;


}


enum class AuthExpInfo(val code: Int, val msg: String) : SysExpInfo {
    PASSWORD_ERROR(AUTH_ERROR_CODE + 1, "密码异常"),
    ACCOUNT_ERROR(AUTH_ERROR_CODE + 2, "帐户异常"),
    DENIED(AUTH_ERROR_CODE + 3, "权限不足"),
    NOT_LOGIN(AUTH_ERROR_CODE + 4, "未登录"),

}



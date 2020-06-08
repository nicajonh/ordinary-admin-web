package com.llh.server.model

import com.fasterxml.jackson.annotation.JsonIgnore


data class SysUser(
    var username: String,

    @get:JsonIgnore
    var password: String,
    var email: String,

//    /**
//     * 修改密码时用。不与数据库的字段映射。
//     */
//    var newPassword: String,
    var accountStatus: Int
) : BasicModelDSL()


data class SysDept(
    val deptName: String
) : BasicModelDSL()

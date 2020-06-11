package com.llh.server.service.sys

import com.llh.server.pojo.vo.AuthTokenVO
import com.llh.server.pojo.vo.LoginVO

/**
 * AuthService
 *
 * CreatedAt: 2020-06-06 15:45
 *
 * @author llh
 */
interface AuthService {

    fun login(loginVO: LoginVO): AuthTokenVO

}

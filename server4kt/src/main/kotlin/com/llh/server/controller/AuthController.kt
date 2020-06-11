package com.llh.server.controller

import com.llh.server.pojo.JsonWrapper
import com.llh.server.pojo.okResponse
import com.llh.server.pojo.vo.LoginVO
import com.llh.server.service.sys.AuthService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * AuthController 登录登出操作的控制器
 *
 * CreatedAt: 2020-06-11 21:58
 *
 * @author llh
 */
@RestController
@RequestMapping("auth")
@Api("授权服务")
class AuthController : Logging {

    @Autowired
    @Qualifier("authService")
    private lateinit var authService: AuthService

    @PostMapping("login")
    @ApiOperation("登录操作")
    fun login(@RequestBody @Valid loginVO: LoginVO): JsonWrapper {
        val tokenVO = authService.login(loginVO)
        return okResponse(tokenVO)
    }
}

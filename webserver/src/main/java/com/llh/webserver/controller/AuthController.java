package com.llh.webserver.controller;

import cn.hutool.core.util.StrUtil;
import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import com.llh.webserver.pojo.JsonWrapper;
import com.llh.webserver.pojo.auth.AuthTokenVO;
import com.llh.webserver.pojo.auth.LoginVO;
import com.llh.webserver.pojo.auth.RegisterOrUpdateVO;
import com.llh.webserver.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * AuthController
 * <p>
 * CreatedAt: 2020-04-18 17:47
 *
 * @author llh
 */
@RestController
@Api("授权服务")
@RequestMapping("auth")
@Slf4j
public class AuthController extends BasicController {
    @Autowired
    @Qualifier("authService")
    private AuthService authService;

    @PostMapping("register")
    @ApiOperation("注册新用户")
    public JsonWrapper register(@RequestBody RegisterOrUpdateVO registerUserVO) {
        if (StrUtil.isBlank(registerUserVO.getPassword())) {
            log.debug("注册用户时密码不能为空！ {}", registerUserVO);
            throw new BusinessException(BasicExpEnum.DATA_VALIDATE_ERROR);
        }
        authService.register(registerUserVO);
        return JsonWrapper.ok(registerUserVO);
    }

    @PostMapping("login")
    @ApiOperation("登录操作")
    public JsonWrapper login(@RequestBody @Valid LoginVO loginVO) {
        AuthTokenVO tokenVO = authService.login(loginVO);
        return JsonWrapper.ok(tokenVO);
    }
}

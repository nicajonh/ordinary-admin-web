package com.llh.webserver.controller;

import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.JsonWrapper;
import com.llh.webserver.pojo.SimplePageQueryVO;
import com.llh.webserver.pojo.auth.RegisterOrUpdateVO;
import com.llh.webserver.service.AuthService;
import com.llh.webserver.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * SysUserController
 * <p>
 * CreatedAt: 2020-05-21 21:41
 *
 * @author llh
 */
@RestController
@RequestMapping("user")
@Api("用户信息操作模块。")
public class SysUserController extends BasicController {

    @Autowired
    @Qualifier("sysUserService")
    private SysUserService userService;
    @Autowired
    @Qualifier("authService")
    private AuthService authService;

    @GetMapping
    @ApiOperation("获取用户列表，并以分页的形式返回。")
    public JsonWrapper page(SimplePageQueryVO<SysUser> pageQueryVO) {
        Page<SysUser> page = userService.pageQuery(pageQueryVO);
        return JsonWrapper.ok(page);
    }

    @PostMapping
    @ApiOperation("新增一个用户。")
    public JsonWrapper add(@RequestBody RegisterOrUpdateVO user) {
        RegisterOrUpdateVO register = authService.register(user);
        return JsonWrapper.ok(register);
    }

    @GetMapping("{userId}")
    @ApiOperation("根据用户id获取用户信息。")
    public JsonWrapper userInfo(@PathVariable("userId") String userId) {
        SysUser user = userService.findById(userId);
        return JsonWrapper.ok(user);
    }

    @PostMapping("update")
    @ApiOperation("更新用户信息。")
    public JsonWrapper update(@RequestBody SysUser user) {
        userService.updateById(user);
        return JsonWrapper.ok(user);
    }

}

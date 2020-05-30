package com.llh.webserver.controller;

import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.JsonWrapper;
import com.llh.webserver.pojo.SimplePageQueryVO;
import com.llh.webserver.pojo.auth.RegisterOrUpdateVO;
import com.llh.webserver.service.AuthService;
import com.llh.webserver.service.SysUserService;
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
public class SysUserController extends BasicController {

    @Autowired
    @Qualifier("sysUserService")
    private SysUserService userService;
    @Autowired
    @Qualifier("authService")
    private AuthService authService;

    @GetMapping
    public JsonWrapper page(SimplePageQueryVO<SysUser> pageQueryVO) {
        Page<SysUser> page = userService.pageQuery(pageQueryVO);
        return JsonWrapper.ok(page);
    }

    @PostMapping
    public JsonWrapper add(@RequestBody RegisterOrUpdateVO user) {
        RegisterOrUpdateVO register = authService.register(user);
        return JsonWrapper.ok(register);
    }

    @GetMapping("{userId}")
    public JsonWrapper userInfo(@PathVariable("userId") String userId) {
        SysUser user = userService.findById(userId);
        return JsonWrapper.ok(user);
    }

    @PostMapping("update")
    public JsonWrapper update(@RequestBody SysUser user) {
        userService.updateById(user);
        return JsonWrapper.ok(user);
    }

}

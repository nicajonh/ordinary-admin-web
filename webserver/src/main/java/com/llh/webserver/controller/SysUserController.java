package com.llh.webserver.controller;

import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.JsonWrapper;
import com.llh.webserver.pojo.SimplePageQueryVO;
import com.llh.webserver.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public JsonWrapper page(SimplePageQueryVO<SysUser> pageQueryVO) {
        Page<SysUser> page = userService.pageQuery(pageQueryVO);
        return JsonWrapper.ok(page);

    }

}

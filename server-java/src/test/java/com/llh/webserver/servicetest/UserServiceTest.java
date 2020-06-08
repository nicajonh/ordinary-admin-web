package com.llh.webserver.servicetest;

import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.SimplePageQueryVO;
import com.llh.webserver.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static java.util.stream.Collectors.toList;

/**
 * UserServiceTest
 * <p>
 * CreatedAt: 2020-05-21 23:12
 *
 * @author llh
 */
@SpringBootTest
public class UserServiceTest {


    @Autowired
    @Qualifier("sysUserService")
    private SysUserService userService;

    @Test
    public void testPage() {
        SimplePageQueryVO<SysUser> queryVO = new SimplePageQueryVO<>();
        SysUser user = new SysUser();
        user.setUsername("Tom");
        queryVO.setPageNumber(0)
            .setOrderField("username")
            .setModel(user)
            .setPageSize(12);
        Page<SysUser> sysUsers = userService.pageQuery(queryVO);
        System.out.println(sysUsers.get().collect(toList()));
    }

}

package com.llh.webserver.daotest;

import com.llh.webserver.model.SysUser;
import com.llh.webserver.repository.SysUserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SysUserDAOTest
 * <p>
 * CreatedAt: 2020-04-18 12:37
 *
 * @author llh
 */
@SpringBootTest
public class SysUserDAOTest {

    @Autowired
    private SysUserRepo userRepo;

    @Test
    public void testAdd() {
        SysUser user = new SysUser();
        user.setUsername("Tom");
        userRepo.save(user);
    }
}

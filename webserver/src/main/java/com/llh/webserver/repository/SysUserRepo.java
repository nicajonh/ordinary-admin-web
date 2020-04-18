package com.llh.webserver.repository;

import com.llh.webserver.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * SysUserDAO
 * <p>
 * CreatedAt: 2020-04-18 12:38
 *
 * @author llh
 */
public interface SysUserRepo extends JpaRepository<SysUser, String> {
    /**
     * 根据用户名查找数据
     *
     * @param username 用户名
     * @return 用户信息。注意判空。
     */
    Optional<SysUser> findTopByUsername(String username);
}

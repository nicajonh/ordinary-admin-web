package com.llh.webserver.service;

import com.llh.webserver.common.constant.AccountStatus;
import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.SimplePageQueryVO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SysUserService
 * <p>
 * CreatedAt: 2020-04-18 16:58
 *
 * @author llh
 */
public interface SysUserService extends BasicService<SysUser>, UserDetailsService {
    /**
     * 帐户锁定状态
     */
    int LOCKED_ACCOUNT = AccountStatus.LOCKED.ordinal();
    /**
     * 帐户移除状态
     */
    int REMOVED_ACCOUNT = AccountStatus.REMOVED.ordinal();
    /**
     * 帐户未激活状态
     */
    int INACTIVE_ACCOUNT = AccountStatus.INACTIVE.ordinal();

    /**
     * 帐户激活状态
     */
    int ACTIVE_ACCOUNT = AccountStatus.ACTIVE.ordinal();

    /**
     * 分页查询方法
     *
     * @param queryVO 查询VO
     * @return 分页查询结果
     */
    Page<SysUser> pageQuery(SimplePageQueryVO<SysUser> queryVO);
}

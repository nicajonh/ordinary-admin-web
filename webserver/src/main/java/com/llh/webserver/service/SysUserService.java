package com.llh.webserver.service;

import com.llh.webserver.common.constant.AccountStatus;
import com.llh.webserver.model.SysUser;
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
}

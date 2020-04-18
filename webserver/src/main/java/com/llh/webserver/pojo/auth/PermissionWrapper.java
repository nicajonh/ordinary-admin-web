package com.llh.webserver.pojo.auth;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * PermissionWrapper 权限字符包装类
 * <p>
 * 由于SpringSecurity的设计，authority是角色或权限的字符值
 * <p>
 * CreatedAt: 2020-04-18 22:30
 *
 * @author llh
 */
@NoArgsConstructor
public class PermissionWrapper implements GrantedAuthority {

    /**
     * 角色或权限的字符值
     */
    private String authority;

    public PermissionWrapper(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }
}

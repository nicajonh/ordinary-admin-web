package com.llh.webserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.llh.webserver.pojo.auth.PermissionWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * SysUser
 * <p>
 * CreatedAt: 2020-04-18 12:01
 *
 * @author llh
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = " data_status = 0 ")
@DynamicUpdate // 只更新有值的部分
@DynamicInsert // 只插入有值的部分
public class SysUser extends BasicModel implements UserDetails {

    @Column(length = 64)
    private String username;
    @JsonIgnore
    private String password;

    /**
     * 修改密码
     */
    @Transient
    @JsonIgnore
    public String newPassword;
    /**
     * 帐户状态
     */
    private Integer accountStatus;

    @Transient
    private Set<PermissionWrapper> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

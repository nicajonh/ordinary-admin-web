package com.llh.server.pojo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * AccountVO 帐户详情类
 *
 * CreatedAt: 2020-06-02 23:49
 *
 * @author llh
 */
class AccountVO(private val account: String,
                private val pwd: String,
                val id: String) : UserDetails {

    private val permsSet = mutableSetOf<PermissionWrapper>()

    fun addAuthority(perm: PermissionWrapper): AccountVO {
        this.permsSet.add(perm)
        return this
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return permsSet
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.account
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return this.pwd
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}

class PermissionWrapper(private var authority: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return this.authority
    }
}

fun createEmptyAccount(): AccountVO {
    return AccountVO("", "", "")
}

data class RegisterOrUpdateVO(
    val username: String,
    val password: String?,
    val newPassword: String?,
    val id: String?,
    val email: String,
    val accountStatus: Int?
)

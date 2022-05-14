package com.tp.backend.model.auth;

import com.tp.backend.model.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public AuthUserDetails(UserInfo user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.active = user.isEnabled();
        this.authorities = Arrays.asList(() -> user.getUserType().getType());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

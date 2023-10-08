package com.zeroxn.xuecheng.auth.authorization;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 14:59:07
 * @Description:
 */
public class UserInfoAuthenticationToken extends AbstractAuthenticationToken implements AuthenticatedPrincipal {
    private final String userInfo;

    public UserInfoAuthenticationToken(String userInfo, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userInfo = userInfo;
    }
    @Override
    public String getName() {
        return userInfo;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}

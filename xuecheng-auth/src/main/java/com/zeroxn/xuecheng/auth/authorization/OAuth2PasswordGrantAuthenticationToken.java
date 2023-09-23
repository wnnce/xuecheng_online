package com.zeroxn.xuecheng.auth.authorization;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Set;

/**
 * @Author: lisang
 * @DateTime: 2023-09-23 21:38:39
 * @Description:
 */
@Getter
public class OAuth2PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    private final String username;
    private final String password;
    private final String clientId;
    private final Set<String> scopes;

    public OAuth2PasswordGrantAuthenticationToken(String username, String password, Authentication authentication, Set<String> scopes) {
        super(OAuth2PasswordGrantAuthenticationConverter.PASSWORD_GRANT_TYPE, authentication, null);
        this.username = username;
        this.password = password;
        this.clientId = authentication.getName();
        this.scopes = scopes;
    }
}

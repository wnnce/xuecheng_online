package com.zeroxn.xuecheng.auth.authorization.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

/**
 * @Author: lisang
 * @DateTime: 2023-09-23 22:45:49
 * @Description:
 */
public final class OAuth2AuthenticationProviderUtils {
    public static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken authenticationToken = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            authenticationToken = (OAuth2ClientAuthenticationToken) authentication;
        }
        if (authenticationToken != null && authenticationToken.isAuthenticated()) {
            return authenticationToken;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }
}

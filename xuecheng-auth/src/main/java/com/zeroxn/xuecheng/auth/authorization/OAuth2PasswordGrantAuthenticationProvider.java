package com.zeroxn.xuecheng.auth.authorization;

import com.zeroxn.xuecheng.auth.authorization.utils.OAuth2AuthenticationProviderUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: lisang
 * @DateTime: 2023-09-23 21:36:04
 * @Description:
 */
public class OAuth2PasswordGrantAuthenticationProvider implements AuthenticationProvider {
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType("id_token");
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public OAuth2PasswordGrantAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
                                                     OAuth2AuthorizationService authorizationService,
                                                     OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // todo 用户信息无法保存到token中

        OAuth2PasswordGrantAuthenticationToken passwordAuthenticationToken = (OAuth2PasswordGrantAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient((Authentication) passwordAuthenticationToken.getPrincipal());
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes().contains(passwordAuthenticationToken.getGrantType())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
        Set<String> scopes = passwordAuthenticationToken.getScopes();

        if (scopes != null && !scopes.isEmpty()) {
            for (String scope : scopes) {
                Set<String> registeredScopes = registeredClient.getScopes();
                if (!registeredScopes.contains(scope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
                }
            }
        }else {
            scopes = registeredClient.getScopes();
        }
        String username = passwordAuthenticationToken.getUsername();
        String password = passwordAuthenticationToken.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new OAuth2AuthenticationException("The resource does not exist or the credentials are invalid");
        }
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(scopes)
                .authorizationGrantType(passwordAuthenticationToken.getGrantType())
                .authorizationGrant(passwordAuthenticationToken);

        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the access token.", null);
            throw new OAuth2AuthenticationException(error);
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), scopes);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", userDetails.getUsername());
        tokenData.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        if (!scopes.isEmpty()) {
            tokenData.put("scopes", scopes);
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization
                .withRegisteredClient(registeredClient)
                .principalName(userDetails.getUsername())
                .authorizationGrantType(passwordAuthenticationToken.getGrantType())
                .token(accessToken, m -> m.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, tokenData));

        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getTokenSettings().isReuseRefreshTokens()) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the refresh token.", null);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            builder.refreshToken(refreshToken);
        }

        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (scopes.contains("openid")) {
            tokenContext = tokenContextBuilder
                    .tokenType(ID_TOKEN_TOKEN_TYPE)
                    .authorization(builder.build())
                    .build();
            OAuth2Token generatedIdToken = tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt jwtToken)) {
                OAuth2Error error = new OAuth2Error("server_error", "The token generator failed to generate the ID token.", "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
                throw new OAuth2AuthenticationException(error);
            }
            OidcIdToken idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(), jwtToken.getClaims());
            builder.token(idToken, m -> m.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
            additionalParameters = new HashMap<>();
            additionalParameters.put("id_token", idToken.getTokenValue());
        }

        OAuth2AccessTokenAuthenticationToken authenticationToken = null;
        if (refreshToken != null && !additionalParameters.isEmpty()) {
            authenticationToken = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
        }else if (refreshToken != null) {
            authenticationToken = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken);
        }else {
            authenticationToken = new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
        }

        authorizationService.save(builder.build());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
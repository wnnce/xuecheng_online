package com.zeroxn.xuecheng.auth.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroxn.xuecheng.auth.authorization.utils.OAuth2AuthenticationProviderUtils;
import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;
import com.zeroxn.xuecheng.auth.service.AuthService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

import java.util.*;

/**
 * @Author: lisang
 * @DateTime: 2023-09-23 21:36:04
 * @Description:
 */
public class OAuth2PasswordGrantAuthenticationProvider implements AuthenticationProvider {
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType("id_token");
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    public OAuth2PasswordGrantAuthenticationProvider(OAuth2AuthorizationService authorizationService, ObjectMapper objectMapper,
                                                     OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                     ApplicationContext applicationContext) {
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
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
        String authParamsJson = passwordAuthenticationToken.getUsername();
        AuthParamsDto authParams = null;
        try{
            authParams = objectMapper.readValue(authParamsJson, AuthParamsDto.class);
        }catch (JsonProcessingException e) {
            throw new OAuth2AuthenticationException("The requested data does not meet the requirements");
        }
        AuthService authService = null;
        switch (authParams.getAuthType()){
            case "password" -> authService = applicationContext.getBean("PasswordAuthService", AuthService.class);
            case "wx" -> authService = applicationContext.getBean("WechatAuthService", AuthService.class);
            case "sms" -> authService = applicationContext.getBean("SMSAuthService", AuthService.class);
            default -> throw new OAuth2AuthenticationException("Auth types are not supported");
        }
        UserExtend userExtend = authService.execute(authParams);
        if (userExtend == null) {
            throw new OAuth2AuthenticationException("The resource does not exist or the credentials are invalid");
        }
        userExtend.setPassword(null);
        String userInfoJson;
        try {
            userInfoJson = objectMapper.writeValueAsString(userExtend);
        }catch (JsonProcessingException e) {
            throw new OAuth2AuthenticationException("Serialization of user information failed");
        }
        UserInfoAuthenticationToken principal= new UserInfoAuthenticationToken(userInfoJson, null);
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(principal)
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
        tokenData.put("username", userExtend);
//        tokenData.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        if (!scopes.isEmpty()) {
            tokenData.put("scopes", scopes);
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization
                .withRegisteredClient(registeredClient)
                .principalName(userExtend.getUsername())
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
        OAuth2AccessTokenAuthenticationToken authenticationToken;
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
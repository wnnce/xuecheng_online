package com.zeroxn.xuecheng.auth.config;

/**
 * @Author: lisang
 * @DateTime: 2023-09-24 11:58:25
 * @Description:
 */
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Map;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.StringUtils;

final class OAuth2ConfigurerUtils {
    private OAuth2ConfigurerUtils() {
    }
    static RegisteredClientRepository getRegisteredClientRepository(HttpSecurity httpSecurity) {
        RegisteredClientRepository registeredClientRepository = (RegisteredClientRepository)httpSecurity.getSharedObject(RegisteredClientRepository.class);
        if (registeredClientRepository == null) {
            registeredClientRepository = (RegisteredClientRepository)getBean(httpSecurity, RegisteredClientRepository.class);
            httpSecurity.setSharedObject(RegisteredClientRepository.class, registeredClientRepository);
        }

        return registeredClientRepository;
    }

    static OAuth2AuthorizationService getAuthorizationService(HttpSecurity httpSecurity) {
        OAuth2AuthorizationService authorizationService = (OAuth2AuthorizationService)httpSecurity.getSharedObject(OAuth2AuthorizationService.class);
        if (authorizationService == null) {
            authorizationService = (OAuth2AuthorizationService)getOptionalBean(httpSecurity, OAuth2AuthorizationService.class);
            if (authorizationService == null) {
                authorizationService = new InMemoryOAuth2AuthorizationService();
            }

            httpSecurity.setSharedObject(OAuth2AuthorizationService.class, authorizationService);
        }

        return (OAuth2AuthorizationService)authorizationService;
    }

    static OAuth2AuthorizationConsentService getAuthorizationConsentService(HttpSecurity httpSecurity) {
        OAuth2AuthorizationConsentService authorizationConsentService = (OAuth2AuthorizationConsentService)httpSecurity.getSharedObject(OAuth2AuthorizationConsentService.class);
        if (authorizationConsentService == null) {
            authorizationConsentService = (OAuth2AuthorizationConsentService)getOptionalBean(httpSecurity, OAuth2AuthorizationConsentService.class);
            if (authorizationConsentService == null) {
                authorizationConsentService = new InMemoryOAuth2AuthorizationConsentService();
            }

            httpSecurity.setSharedObject(OAuth2AuthorizationConsentService.class, authorizationConsentService);
        }

        return (OAuth2AuthorizationConsentService)authorizationConsentService;
    }

    static OAuth2TokenGenerator<? extends OAuth2Token> getTokenGenerator(HttpSecurity httpSecurity) {
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = (OAuth2TokenGenerator)httpSecurity.getSharedObject(OAuth2TokenGenerator.class);
        if (tokenGenerator == null) {
            tokenGenerator = (OAuth2TokenGenerator)getOptionalBean(httpSecurity, OAuth2TokenGenerator.class);
            if (tokenGenerator == null) {
                JwtGenerator jwtGenerator = getJwtGenerator(httpSecurity);
                OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
                OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer = getAccessTokenCustomizer(httpSecurity);
                if (accessTokenCustomizer != null) {
                    accessTokenGenerator.setAccessTokenCustomizer(accessTokenCustomizer);
                }

                OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
                if (jwtGenerator != null) {
                    tokenGenerator = new DelegatingOAuth2TokenGenerator(new OAuth2TokenGenerator[]{jwtGenerator, accessTokenGenerator, refreshTokenGenerator});
                } else {
                    tokenGenerator = new DelegatingOAuth2TokenGenerator(new OAuth2TokenGenerator[]{accessTokenGenerator, refreshTokenGenerator});
                }
            }

            httpSecurity.setSharedObject(OAuth2TokenGenerator.class, tokenGenerator);
        }

        return (OAuth2TokenGenerator)tokenGenerator;
    }

    private static JwtGenerator getJwtGenerator(HttpSecurity httpSecurity) {
        JwtGenerator jwtGenerator = (JwtGenerator)httpSecurity.getSharedObject(JwtGenerator.class);
        if (jwtGenerator == null) {
            JwtEncoder jwtEncoder = getJwtEncoder(httpSecurity);
            if (jwtEncoder != null) {
                jwtGenerator = new JwtGenerator(jwtEncoder);
                OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer = getJwtCustomizer(httpSecurity);
                if (jwtCustomizer != null) {
                    jwtGenerator.setJwtCustomizer(jwtCustomizer);
                }

                httpSecurity.setSharedObject(JwtGenerator.class, jwtGenerator);
            }
        }

        return jwtGenerator;
    }

    private static JwtEncoder getJwtEncoder(HttpSecurity httpSecurity) {
        JwtEncoder jwtEncoder = (JwtEncoder)httpSecurity.getSharedObject(JwtEncoder.class);
        if (jwtEncoder == null) {
            jwtEncoder = (JwtEncoder)getOptionalBean(httpSecurity, JwtEncoder.class);
            if (jwtEncoder == null) {
                JWKSource<SecurityContext> jwkSource = getJwkSource(httpSecurity);
                if (jwkSource != null) {
                    jwtEncoder = new NimbusJwtEncoder(jwkSource);
                }
            }

            if (jwtEncoder != null) {
                httpSecurity.setSharedObject(JwtEncoder.class, jwtEncoder);
            }
        }

        return (JwtEncoder)jwtEncoder;
    }

    static JWKSource<SecurityContext> getJwkSource(HttpSecurity httpSecurity) {
        JWKSource<SecurityContext> jwkSource = (JWKSource)httpSecurity.getSharedObject(JWKSource.class);
        if (jwkSource == null) {
            ResolvableType type = ResolvableType.forClassWithGenerics(JWKSource.class, new Class[]{SecurityContext.class});
            jwkSource = (JWKSource)getOptionalBean(httpSecurity, type);
            if (jwkSource != null) {
                httpSecurity.setSharedObject(JWKSource.class, jwkSource);
            }
        }

        return jwkSource;
    }

    private static OAuth2TokenCustomizer<JwtEncodingContext> getJwtCustomizer(HttpSecurity httpSecurity) {
        ResolvableType type = ResolvableType.forClassWithGenerics(OAuth2TokenCustomizer.class, new Class[]{JwtEncodingContext.class});
        return (OAuth2TokenCustomizer)getOptionalBean(httpSecurity, type);
    }

    private static OAuth2TokenCustomizer<OAuth2TokenClaimsContext> getAccessTokenCustomizer(HttpSecurity httpSecurity) {
        ResolvableType type = ResolvableType.forClassWithGenerics(OAuth2TokenCustomizer.class, new Class[]{OAuth2TokenClaimsContext.class});
        return (OAuth2TokenCustomizer)getOptionalBean(httpSecurity, type);
    }

    static AuthorizationServerSettings getAuthorizationServerSettings(HttpSecurity httpSecurity) {
        AuthorizationServerSettings authorizationServerSettings = (AuthorizationServerSettings)httpSecurity.getSharedObject(AuthorizationServerSettings.class);
        if (authorizationServerSettings == null) {
            authorizationServerSettings = (AuthorizationServerSettings)getBean(httpSecurity, AuthorizationServerSettings.class);
            httpSecurity.setSharedObject(AuthorizationServerSettings.class, authorizationServerSettings);
        }

        return authorizationServerSettings;
    }

    static <T> T getBean(HttpSecurity httpSecurity, Class<T> type) {
        return ((ApplicationContext)httpSecurity.getSharedObject(ApplicationContext.class)).getBean(type);
    }

    static <T> T getBean(HttpSecurity httpSecurity, ResolvableType type) {
        ApplicationContext context = (ApplicationContext)httpSecurity.getSharedObject(ApplicationContext.class);
        String[] names = context.getBeanNamesForType(type);
        if (names.length == 1) {
            return (T) context.getBean(names[0]);
        } else if (names.length > 1) {
            throw new NoUniqueBeanDefinitionException(type, names);
        } else {
            throw new NoSuchBeanDefinitionException(type);
        }
    }

    static <T> T getOptionalBean(HttpSecurity httpSecurity, Class<T> type) {
        Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)httpSecurity.getSharedObject(ApplicationContext.class), type);
        if (beansMap.size() > 1) {
            int var10003 = beansMap.size();
            String var10004 = type.getName();
            throw new NoUniqueBeanDefinitionException(type, var10003, "Expected single matching bean of type '" + var10004 + "' but found " + beansMap.size() + ": " + StringUtils.collectionToCommaDelimitedString(beansMap.keySet()));
        } else {
            return !beansMap.isEmpty() ? beansMap.values().iterator().next() : null;
        }
    }

    static <T> T getOptionalBean(HttpSecurity httpSecurity, ResolvableType type) {
        ApplicationContext context = (ApplicationContext)httpSecurity.getSharedObject(ApplicationContext.class);
        String[] names = context.getBeanNamesForType(type);
        if (names.length > 1) {
            throw new NoUniqueBeanDefinitionException(type, names);
        } else {
            return names.length == 1 ? (T) context.getBean(names[0]) : null;
        }
    }
}

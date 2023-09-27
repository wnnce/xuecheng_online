package com.zeroxn.xuecheng.auth.authorization;

import com.zeroxn.xuecheng.auth.authorization.utils.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @Author: lisang
 * @DateTime: 2023-09-23 19:42:12
 * @Description:
 */
public class OAuth2PasswordGrantAuthenticationConverter implements AuthenticationConverter {
    protected static final AuthorizationGrantType PASSWORD_GRANT_TYPE = new AuthorizationGrantType("password");
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!PASSWORD_GRANT_TYPE.getValue().equals(grantType)) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            OAuth2EndpointUtils.throwError("invalid_request", OAuth2ParameterNames.CLIENT_ID, ERROR_URI);
        }
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String clientId  = parameters.getFirst(OAuth2ParameterNames.CLIENT_ID);
        if (!StringUtils.hasText(clientId) || parameters.get(OAuth2ParameterNames.CLIENT_ID).size() != 1 ) {
            OAuth2EndpointUtils.throwError("invalid_request", OAuth2ParameterNames.CLIENT_ID, ERROR_URI);
        }
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
            OAuth2EndpointUtils.throwError("invalid_request", OAuth2ParameterNames.USERNAME, ERROR_URI);
        }
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
            OAuth2EndpointUtils.throwError("invalid_request", OAuth2ParameterNames.PASSWORD, ERROR_URI);
        }
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        Set<String> scopes = scope != null ? Set.of(scope.split(" ")) : null;
        return new OAuth2PasswordGrantAuthenticationToken(username, password, authentication, scopes);
    }
}

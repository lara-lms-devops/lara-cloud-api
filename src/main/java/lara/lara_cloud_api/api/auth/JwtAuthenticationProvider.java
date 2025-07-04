package lara.lara_cloud_api.api.auth;

import io.jsonwebtoken.Claims;
import lara.lara_cloud_api.api.service.UserService;
import lara.lara_cloud_api.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.UUID;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var authenticationRequest = (JwtAuthenticationToken) authentication;
        // TODO add authorization roles
        // TODO add the organization roles OR super role if it is not in the organization context, maybe block users with no permission here

        final Claims jwtClaims = jwtService.validateAndGetClaims(authenticationRequest.getToken());
        final User user = userService.findById(UUID.fromString(jwtClaims.getSubject()))
                .orElseThrow(() -> new RuntimeException()); // FIXME change to unauthorized

        return JwtAuthenticationToken.authenticated(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

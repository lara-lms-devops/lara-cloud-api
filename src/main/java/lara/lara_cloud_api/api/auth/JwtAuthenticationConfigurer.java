package lara.lara_cloud_api.api.auth;

import lara.lara_cloud_api.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void init(HttpSecurity http) {
        http.authenticationProvider(new JwtAuthenticationProvider(jwtService, userService));
    }

    @Override
    public void configure(HttpSecurity http) {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager),
                AuthorizationFilter.class
        );
    }

}

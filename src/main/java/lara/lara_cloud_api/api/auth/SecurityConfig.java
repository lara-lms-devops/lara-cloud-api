package lara.lara_cloud_api.api.auth;

import lara.lara_cloud_api.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // TODO check if it is possible to add a secret
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    JwtAuthenticationConfigurer jwtAuthenticationConfigurer(JwtService jwtService, UserService userService) {
        return new JwtAuthenticationConfigurer(jwtService, userService);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConfigurer jwtConfigurer) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization -> {
                    authorization.requestMatchers("/api/v1/authentication/login").permitAll();
                    authorization.requestMatchers("/api/v1/authentication/register").permitAll();
                    authorization.anyRequest().authenticated();
                })
                // TODO check what should be disabled
                .with(jwtConfigurer, Customizer.withDefaults())
                .build();
    }

}

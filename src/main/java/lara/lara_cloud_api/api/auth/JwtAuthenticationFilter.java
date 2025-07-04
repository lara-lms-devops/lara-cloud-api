package lara.lara_cloud_api.api.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (doesNotHaveJwtToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        var authenticationRequest = JwtAuthenticationToken.unauthenticated(jwtToken);

        try {
            var authentication = authenticationManager.authenticate(authenticationRequest);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e); // FIXME add correct exception
        }

        filterChain.doFilter(request, response);
    }

    private boolean doesNotHaveJwtToken(HttpServletRequest request) {
        return !hasJwtToken(request);
    }

    private boolean hasJwtToken(HttpServletRequest request) {
        return ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(String::toLowerCase)
                .map(value -> value.startsWith("bearer "))
                .orElse(false);
    }

}

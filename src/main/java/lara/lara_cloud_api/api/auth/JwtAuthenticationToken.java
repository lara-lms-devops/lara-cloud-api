package lara.lara_cloud_api.api.auth;

import lara.lara_cloud_api.domain.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    @Getter
    private final String token;
    private final User user; // TODO refine this information

    private JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User user) {
        super(authorities);
        super.setAuthenticated(true);
        this.token = null;
        this.user = user;
    }

    private JwtAuthenticationToken(String jwt) { // TODO create one that extracts the header value
        super(NO_AUTHORITIES);
        super.setAuthenticated(false);
        this.token = jwt;
        this.user = null;
    }

    public static JwtAuthenticationToken unauthenticated(String jwtToken) {
        return new JwtAuthenticationToken(jwtToken);
    }

    public static JwtAuthenticationToken authenticated(User user) {
        // TODO return authenticated with the authorities, user information
        return new JwtAuthenticationToken(NO_AUTHORITIES, user);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }
}

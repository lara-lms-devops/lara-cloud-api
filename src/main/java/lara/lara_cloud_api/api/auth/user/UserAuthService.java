package lara.lara_cloud_api.api.auth.user;

import lara.lara_cloud_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + username)); // TODO improve this later

        return UserDetailsImp.builder() // TODO implement my own UserDetails
                .email(user.getEmail())
                .password(user.getHashedPassword())
                .userId(user.getUuid())
                // TODO add super user role if the user have it
                .build();
    }
}

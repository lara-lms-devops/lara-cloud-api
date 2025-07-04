package lara.lara_cloud_api.api.service;

import lara.lara_cloud_api.api.entities.request.UserRegistrationRequest;
import lara.lara_cloud_api.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationRequest request) {
        if (userService.findUserByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already registered"); // FIXME add correct exception
        }

        var user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setHashedPassword(passwordEncoder.encode(request.password()));

        userService.save(user);
    }
}

package lara.lara_cloud_api.api.controller;

import lara.lara_cloud_api.api.auth.JwtService;
import lara.lara_cloud_api.api.auth.user.UserDetailsImp;
import lara.lara_cloud_api.api.entities.request.UserLoginRequest;
import lara.lara_cloud_api.api.entities.request.UserRegistrationRequest;
import lara.lara_cloud_api.api.entities.response.UserLoginResponse;
import lara.lara_cloud_api.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request) {
        var token = UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password());

        var userDetailsImp = (UserDetailsImp) authenticationManager.authenticate(token)
                .getPrincipal(); // TODO change the authentication to

//        log.info("Authentication object: {}", authentication);

        return new UserLoginResponse(jwtService.generateToken(userDetailsImp.userId()));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/register")
    public void register(@RequestBody UserRegistrationRequest request) {
        authenticationService.registerUser(request);
    }
}

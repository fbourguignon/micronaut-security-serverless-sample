package br.com.security.service;

import br.com.security.model.User;
import br.com.security.exception.InvalidCredentialsException;
import br.com.security.utils.PasswordEncoder;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class AuthenticationService  {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User validateUserCredentials(String email, String password) {
        User user = userService.findUserByEmail(email);

        if(passwordEncoder.validatePassword(password,user.getPassword())){
            log.error("Unable to authenticate the user with provided credentials");
            throw new InvalidCredentialsException("Unable to authenticate the user with provided credentials");
        }

        return user;
    }
}

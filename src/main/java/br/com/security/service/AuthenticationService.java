package br.com.security.service;

import br.com.security.model.User;
import br.com.security.exception.InvalidCredentialsException;
import br.com.security.utils.PasswordEncoder;
import jakarta.inject.Singleton;

@Singleton
public class AuthenticationService  {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User validateUserCredentials(String email, String password) throws Exception {
        User user = userService.findUserByEmail(email);

        if(passwordEncoder.validatePassword(password,user.getPassword())){
            throw new InvalidCredentialsException("");
        }

        return user;
    }
}

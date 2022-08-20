package br.com.security.controller;

import br.com.security.dto.UserProfileResponseDTO;
import br.com.security.mapper.UserProfileMapper;
import br.com.security.service.UserService;
import br.com.security.mapper.RegisterUserMapper;
import br.com.security.dto.RegisterUserRequestDTO;
import br.com.security.dto.RegisterUserResponseDTO;
import br.com.security.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller("users")
public class UserController {

    private final UserService service;
    private final RegisterUserMapper registerUserMapper;
    private final UserProfileMapper userProfileMapper;

    public UserController(UserService service, RegisterUserMapper mapper, UserProfileMapper userProfileMapper) {
        this.service = service;
        this.registerUserMapper = mapper;
        this.userProfileMapper = userProfileMapper;
    }

    @Post("register")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<RegisterUserResponseDTO> save(@Valid @Body RegisterUserRequestDTO request){
        log.info("Handle request to create user");
        User newUser = service.saveUser(registerUserMapper.toEntity(request));
        return HttpResponse.created(registerUserMapper.toResponse(newUser));
    }

    @Get("profile")
    @Secured({"ROLE_USER"})
    public HttpResponse<UserProfileResponseDTO> getProfile(Principal principal){
        log.info("Handle request to get profile");
        User authenticatedUser = service.findUserByEmail(principal.getName());
        return HttpResponse.ok(userProfileMapper.toResponse(authenticatedUser));
    }
}

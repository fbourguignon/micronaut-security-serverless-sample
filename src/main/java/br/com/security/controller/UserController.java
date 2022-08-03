package br.com.security.controller;

import br.com.security.service.UserService;
import br.com.security.mapper.RegisterUserMapper;
import br.com.security.dto.RegisterUserRequestDTO;
import br.com.security.dto.RegisterUserResponseDTO;
import br.com.security.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Controller("users")
@Secured(SecurityRule.IS_ANONYMOUS)
public class UserController {

    private final UserService service;
    private final RegisterUserMapper mapper;

    public UserController(UserService service, RegisterUserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Post("register")
    public HttpResponse<RegisterUserResponseDTO> save(@Valid @Body RegisterUserRequestDTO request){
        log.info("Handle request to create user");
        User newUser = service.saveUser(mapper.toEntity(request));
        return HttpResponse.created(mapper.toResponse(newUser));
    }
}

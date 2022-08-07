package br.com.security.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
@Introspected
public class RegisterUserRequestDTO {

    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;

}

package br.com.security.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;
@Value
@Builder
@AllArgsConstructor
@Introspected
public class RegisterUserResponseDTO {
    private UUID uuid;
    private String email;
}

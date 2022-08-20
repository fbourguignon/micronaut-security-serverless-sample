package br.com.security.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@Introspected
public class UserProfileResponseDTO {
    private UUID uuid;
    private String email;
}

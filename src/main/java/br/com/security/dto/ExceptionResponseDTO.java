package br.com.security.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

@Value
@Builder
@Introspected
public class ExceptionResponseDTO {
    private String message;
}

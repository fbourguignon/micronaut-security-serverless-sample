package br.com.security.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.util.List;

@Value
@Builder
@Introspected
public class ValidationExceptionResponseDTO {
    private List<String> validationMessages;
}

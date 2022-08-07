package br.com.security.exception.handler;

import br.com.security.dto.ValidationExceptionResponseDTO;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Primary
@Produces
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
public class ConstraintExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<ValidationExceptionResponseDTO>> {

    @Override
    public HttpResponse handle(HttpRequest request, ConstraintViolationException exception) {

        var response = ValidationExceptionResponseDTO
                .builder()
                .validationMessages(exception.getConstraintViolations().stream().map(constraintViolation -> constraintViolation.getMessage()).collect(Collectors.toList()))
                .build();

        return HttpResponse
                .badRequest()
                .body(response);
    }
}
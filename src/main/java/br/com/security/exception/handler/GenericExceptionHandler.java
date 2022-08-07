package br.com.security.exception.handler;

import br.com.security.dto.ExceptionResponseDTO;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Handler;

@Slf4j
@Produces
@Singleton
@Requires(classes = {Exception.class, Handler.class})
public class GenericExceptionHandler implements ExceptionHandler<Exception, HttpResponse> {
    public HttpResponse<ExceptionResponseDTO> handle(HttpRequest request, Exception exception) {
        log.error("An generic exception was caught by the handler [{}]", exception.getMessage());

        var response = ExceptionResponseDTO
                .builder()
                .message(exception.getMessage())
                .build();

        return HttpResponse
                .serverError()
                .body(response);
    }
}
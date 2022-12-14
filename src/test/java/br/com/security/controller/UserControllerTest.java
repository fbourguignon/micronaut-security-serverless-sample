package br.com.security.controller;

import br.com.security.dto.*;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends AbstractControllerTest{

    @Test
    @Order(1)
    void registerUser_Success() throws JsonProcessingException {

        var productRequest = RegisterUserRequestDTO.builder()
                .email("email@gmail.com")
                .password("123456")
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/users/register", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(response.getBody(), RegisterUserResponseDTO.class);

        assertEquals(HttpStatus.CREATED.getCode(), response.getStatusCode());
        assertNotNull(productResponse.getUuid());
        assertNotNull(productResponse.getEmail());
    }

    @Test
    @Order(2)
    void registerUser_EmailAlreadyExists() throws JsonProcessingException {

        var productRequest = RegisterUserRequestDTO.builder()
                .email("email@gmail.com")
                .password("123456")
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/users/register", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        final var exceptionResponseDTO = objectMapper.readValue(response.getBody(), ExceptionResponseDTO.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.getCode(), response.getStatusCode());
    }

    @Test
    @Order(3)
    void registerUser_WithoutRequiredFields() throws JsonProcessingException {

        var productRequest = RegisterUserRequestDTO.builder()
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/users/register", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        final var validationExceptionResponse = objectMapper.readValue(response.getBody(), ValidationExceptionResponseDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST.getCode(), response.getStatusCode());
        assertEquals(2, validationExceptionResponse.getValidationMessages().size());
        assertTrue(validationExceptionResponse.getValidationMessages().contains("Password is required"));
        assertTrue(validationExceptionResponse.getValidationMessages().contains("Email is required"));
    }

    @Test
    @Order(4)
    void retrieveUserProfile_Success() throws JsonProcessingException {

        var productRequest = RegisterUserRequestDTO.builder()
                .build();

        AwsProxyRequest authenticationRequest = new AwsProxyRequestBuilder("/login", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{\"username\": \"user@gmail.com\", \"password\": \"12345678\"}")
                .build();

        var response = handleApiGatewayRequest(authenticationRequest);
        final Map<String,String> authenticationResponse = objectMapper.readValue(response.getBody(), Map.class);

        AwsProxyRequest profileRequest = new AwsProxyRequestBuilder("/users/profile", HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ authenticationResponse.get("access_token"))
                .build();

        response = handleApiGatewayRequest(profileRequest);
        final UserProfileResponseDTO userProfileResponse = objectMapper.readValue(response.getBody(), UserProfileResponseDTO.class);

        assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        assertEquals("user@gmail.com", userProfileResponse.getEmail());
        assertNotNull(userProfileResponse.getUuid());

    }
}

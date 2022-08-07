package br.com.security.controller;

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerTest extends AbstractControllerTest{

    @Test
    @Order(1)
    void authenticateUser_Success() {

        AwsProxyRequest request = new AwsProxyRequestBuilder("/login", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{\"username\": \"user@gmail.com\", \"password\": \"12345678\"}")
                .build();

        final var response = handleApiGatewayRequest(request);

        assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());

    }

    @Test
    @Order(2)
    void authenticateUser_WrongCredentials() {

        AwsProxyRequest request = new AwsProxyRequestBuilder("/login", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{\"username\": \"userwrong@gmail.com\", \"password\": \"12345678\"}")
                .build();

        final var response = handleApiGatewayRequest(request);

        assertEquals(HttpStatus.UNAUTHORIZED.getCode(), response.getStatusCode());

    }

}

package br.com.security.controller;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractControllerTest {

    protected static MicronautLambdaHandler handler;
    protected static Context lambdaContext = new MockLambdaContext();
    protected static ObjectMapper objectMapper;

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    protected static void cleanupSpec() {
        handler.getApplicationContext().close();
    }


    protected AwsProxyResponse handleApiGatewayRequest(AwsProxyRequest request) {
        return handler.handleRequest(request, lambdaContext);
    }

    protected String createJsonBoby(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
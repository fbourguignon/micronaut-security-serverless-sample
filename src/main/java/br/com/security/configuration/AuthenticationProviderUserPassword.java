package br.com.security.configuration;

import br.com.security.model.Role;
import br.com.security.model.User;
import br.com.security.service.AuthenticationService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public AuthenticationProviderUserPassword(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            try{
                User user = authenticationService.validateUserCredentials((String) authenticationRequest.getIdentity(), (String) authenticationRequest.getSecret());
                emitter.success(buildSuccessAuthenticationResponse(user));
            }catch (Exception e){
                e.printStackTrace();
                emitter.error(AuthenticationResponse.exception());
            }
        });

    }

    private AuthenticationResponse buildSuccessAuthenticationResponse(User user){
        List<String> roles = user.getRoles()
                    .stream()
                    .map(Role::getType)
                    .map(roleType -> roleType.name())
                .collect(Collectors.toList());

        return AuthenticationResponse.success(user.getEmail(), roles);
    }
}
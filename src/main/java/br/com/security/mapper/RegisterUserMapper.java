package br.com.security.mapper;

import br.com.security.dto.RegisterUserRequestDTO;
import br.com.security.dto.RegisterUserResponseDTO;
import br.com.security.model.User;
import jakarta.inject.Singleton;

@Singleton
public class RegisterUserMapper {

    public RegisterUserResponseDTO toResponse(User user){
        return RegisterUserResponseDTO.builder()
                .email(user.getEmail())
                .uuid(user.getUuid())
                .build();
    }

    public User toEntity(RegisterUserRequestDTO request){
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }
}

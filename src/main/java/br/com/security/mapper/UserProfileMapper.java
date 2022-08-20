package br.com.security.mapper;

import br.com.security.dto.UserProfileResponseDTO;
import br.com.security.model.User;
import jakarta.inject.Singleton;

@Singleton
public class UserProfileMapper {

    public UserProfileResponseDTO toResponse(User user){
        return UserProfileResponseDTO.builder()
                .email(user.getEmail())
                .uuid(user.getUuid())
                .build();
    }

}

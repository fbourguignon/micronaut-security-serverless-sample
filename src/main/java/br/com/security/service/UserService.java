package br.com.security.service;

import br.com.security.model.Role;
import br.com.security.model.RoleType;
import br.com.security.model.User;
import br.com.security.exception.BusinessException;
import br.com.security.exception.UserNotFoundException;
import br.com.security.repository.UserRepository;
import br.com.security.utils.PasswordEncoder;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User findUserByEmail(String email){
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(()-> new UserNotFoundException());
        } catch (BusinessException b){
            throw b;
        } catch (Exception e){
            throw e;
        }
    }

    public User saveUser(User user){
        try {

            Role role = roleService.findRoleByType(RoleType.ROLE_USER);
            user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
            user.addRole(role);

            return userRepository.save(user);
        } catch (BusinessException b){
            throw b;
        } catch (Exception e){
            throw e;
        }
    }
}

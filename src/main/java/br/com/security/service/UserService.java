package br.com.security.service;

import br.com.security.exception.GenericException;
import br.com.security.exception.UserAlreadyRegisteredException;
import br.com.security.model.Role;
import br.com.security.model.RoleType;
import br.com.security.model.User;
import br.com.security.exception.BusinessException;
import br.com.security.exception.UserNotFoundException;
import br.com.security.repository.UserRepository;
import br.com.security.utils.PasswordEncoder;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;

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
                    .orElseThrow(()-> new UserNotFoundException("User not found"));
        } catch (BusinessException b){
            log.error("An BusinessException exception has occurred on find user by email [{}]", b.getMessage());
            throw b;
        } catch (Exception e){
            log.error("A Exception has occurred on find user by email [{}]", e.getMessage());
            throw new GenericException("A Exception has occurred on find user by email");
        }
    }

    public User saveUser(User user){
        try {

            Role role = roleService.findRoleByType(RoleType.ROLE_USER);
            user.setPassword(passwordEncoder.encodePassword(user.getPassword()));
            user.addRole(role);

            return userRepository.save(user);
        } catch (PersistenceException pe){
            if(pe.getCause() instanceof ConstraintViolationException){
                ConstraintViolationException cv = (ConstraintViolationException) pe.getCause();
                log.error("An ConstraintViolationException has occurred on register user [{}]", cv.getSQLException().getMessage());
                throw new UserAlreadyRegisteredException("User with email already registered");
            }
            throw pe;
        } catch (BusinessException b){
            log.error("An BusinessException exception has occurred on register user [{}]", b.getMessage());
            throw b;
        } catch (Exception e){
            log.error("A Exception has occurred on register user [{}]", e.getMessage());
            throw new GenericException("A Exception has occurred on register user");
        }
    }
}

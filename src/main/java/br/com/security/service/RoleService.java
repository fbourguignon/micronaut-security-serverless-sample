package br.com.security.service;

import br.com.security.model.Role;
import br.com.security.model.RoleType;
import br.com.security.exception.BusinessException;
import br.com.security.exception.RoleNotFoundException;
import br.com.security.repository.RoleRepository;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findRoleByType(RoleType roleType){
        try {
            return repository.findByType(roleType)
                    .orElseThrow(()-> new RoleNotFoundException());
        } catch (BusinessException b){
            throw b;
        } catch (Exception e){
            throw e;
        }
    }
}

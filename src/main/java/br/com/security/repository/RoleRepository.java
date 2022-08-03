package br.com.security.repository;

import br.com.security.model.Role;
import br.com.security.model.RoleType;
import io.micronaut.data.annotation.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {

    Optional<Role> findByType(RoleType roleType);
}

package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.resourceforge.model.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(String roleName);
    
}

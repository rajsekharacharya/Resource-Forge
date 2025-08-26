package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.resourceforge.model.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameAndEmail(String username, String email);

    List<User> findByRoleAndSuperCompanyId(String role, Integer superCompanyId);

    List<User> findByRoleAndSuperCompanyIdAndCoIdAndEnabled(String role, Integer superCompanyId,Integer coId,boolean enabled);

    List<User> findByRole(String role);

    List<User> findByCoIdAndSuperCompanyId(Integer coId,Integer superCompanyId);

    List<User> findByCoId(Integer coId);

    
    Integer countByCoIdAndSuperCompanyId(Integer coId,Integer superCompanyId);

}

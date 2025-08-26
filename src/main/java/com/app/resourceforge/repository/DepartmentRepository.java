package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Department;


public interface DepartmentRepository extends JpaRepository<Department,Long> {

    List<Department> findByStatusAndCompanyIdAndSuperCompanyIdOrderByNameAsc(boolean status, Integer companyId,Integer superCompanyId);
    List<Department> findByCompanyIdAndSuperCompanyIdOrderByNameAsc(Integer companyId, Integer superCompanyId);
    boolean existsByName(String name);
    Optional<Department> findByNameAndCompanyIdAndSuperCompanyId(String name, Integer companyId, Integer superCompanyId);

    boolean existsByNameAndCompanyIdAndSuperCompanyId(String name, Integer companyId, Integer superCompanyId);
    
}

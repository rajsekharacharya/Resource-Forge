package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.JobRole;


public interface JobRoleRepository extends JpaRepository<JobRole,Long> {

    List<JobRole> findByCompanyId(Integer companyId);

    List<JobRole> findByStatusAndCompanyId(boolean status, Integer companyId);

    boolean existsByCompanyIdAndNameAndDepId(Integer companyId,String name ,Long depId);

     Optional<JobRole> findByCompanyIdAndSuperCompanyIdAndNameAndDepId(Integer companyId, Integer superCompanyId,String name ,Long depId);
}

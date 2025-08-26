package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.ProjectMaster;

public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, Long> {

    List<ProjectMaster> findByStatusAndCompanyId(boolean status, Integer companyId);

    List<ProjectMaster> findByCompanyId(Integer companyId);

    boolean existsByNameAndCompanyId(String name, Integer companyId);

    Optional<ProjectMaster> findByNameAndCompanyIdAndSuperCompanyId(String name, Integer companyId,
            Integer superCompanyId);

}

package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findByStatusAndSuperCompanyId(boolean status,Integer superCompanyId);

    List<Company> findBySuperCompanyId(Integer superCompanyId);

    Optional<Company> findByCode(String code);

    Integer countBySuperCompanyId(Integer superCompanyId);
    
}

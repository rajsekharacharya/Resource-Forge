package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByTypeAndStatusAndCompanyId(String type,boolean status, Integer companyId);
    List<Supplier> findByStatusAndCompanyId(boolean status, Integer companyId);

    List<Supplier> findByCompanyId(Integer companyId);

    boolean existsByName(String name);

    boolean existsByNameAndTypeAndCompanyIdAndSuperCompanyId(String name,String type,Integer companyId,Integer superCompanyId);

    Optional<Supplier> findByNameAndTypeAndCompanyIdAndSuperCompanyId(String name,String type,Integer companyId,Integer superCompanyId); 

}

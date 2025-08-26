package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.SubLocation;
import java.util.List;

@Repository
public interface SubLocationRepository extends JpaRepository<SubLocation, Long> {

    List<SubLocation> findByCompanyIdAndSuperCompanyId(Integer companyId, Integer superCompanyId);

    List<SubLocation> findByCompanyIdAndSuperCompanyIdAndStatus(Integer companyId, Integer superCompanyId,
            boolean status);

    List<SubLocation> findByLocIdAndCompanyIdAndSuperCompanyIdAndStatus(Long locId, Integer companyId,
            Integer superCompanyId, boolean status);

}

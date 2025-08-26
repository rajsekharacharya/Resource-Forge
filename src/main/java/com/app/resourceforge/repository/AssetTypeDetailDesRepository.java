package com.app.resourceforge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.AssetTypeDetailDes;

@Repository
public interface AssetTypeDetailDesRepository extends JpaRepository<AssetTypeDetailDes, Long> {

    List<AssetTypeDetailDes> findByCompanyIdAndSuperCompanyId(Integer companyId, Integer superCompanyId);

    List<AssetTypeDetailDes> findByStatusAndCompanyIdAndSuperCompanyId(boolean status, Integer companyId,
            Integer superCompanyId);

}

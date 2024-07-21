package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGiamGiaRepo extends JpaRepository<PhieuGiamGia, Long> , JpaSpecificationExecutor<PhieuGiamGia> {
}

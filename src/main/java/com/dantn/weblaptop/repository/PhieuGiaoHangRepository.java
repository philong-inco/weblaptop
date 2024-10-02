package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.PhieuGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGiaoHangRepository extends JpaRepository<PhieuGiaoHang, Long> , JpaSpecificationExecutor<PhieuGiaoHang> {
}

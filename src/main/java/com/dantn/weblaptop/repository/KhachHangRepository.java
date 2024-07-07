package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
}

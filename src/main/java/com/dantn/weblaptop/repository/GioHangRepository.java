package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.giohang.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {
}

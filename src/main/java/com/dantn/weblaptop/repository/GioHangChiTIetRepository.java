package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTIetRepository extends JpaRepository<GioHangChiTiet, Long> {
}

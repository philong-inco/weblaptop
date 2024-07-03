package com.dantn.weblaptop.hoadon.repository;

import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonHinhThucThanhToanRepository extends JpaRepository<HoaDonHinhThucThanhToan, Long> {
}

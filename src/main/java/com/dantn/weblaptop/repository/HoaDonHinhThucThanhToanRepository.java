package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonHinhThucThanhToanRepository extends JpaRepository<HoaDonHinhThucThanhToan, Long> {
    List<HoaDonHinhThucThanhToan> findByHoaDonMa(String billCode);

}

package com.dantn.weblaptop.hoadon.repository;

import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, Long> {
}

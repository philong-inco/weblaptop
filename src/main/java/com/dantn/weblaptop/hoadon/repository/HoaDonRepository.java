package com.dantn.weblaptop.hoadon.repository;

import com.dantn.weblaptop.entity.hoadon.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
}

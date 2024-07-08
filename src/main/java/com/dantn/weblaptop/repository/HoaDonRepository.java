package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    List<HoaDon> findByTrangThaiAndLoaiHoaDon(HoaDonStatus status, Integer type );
}

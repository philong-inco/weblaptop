package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuaHoaDonRepository extends JpaRepository<LichSuHoaDon, Long> {
    Page<LichSuHoaDon> findAllByHoaDonId(Long billId, Pageable pageable);

    List<LichSuHoaDon> findAllByHoaDonId (Long billId);
}

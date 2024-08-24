package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long>, JpaSpecificationExecutor<HoaDon> {

    List<HoaDon> findByTrangThaiAndLoaiHoaDon(HoaDonStatus status, Integer type);

    Page<HoaDon> findByTrangThaiAndLoaiHoaDon(HoaDonStatus status, Integer type, Pageable pageable);

    Optional<HoaDon> findByIdAndTrangThai(Long id , HoaDonStatus status);

}

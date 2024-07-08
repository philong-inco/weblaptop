package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangPhieuGiamGiaRepository extends JpaRepository<KhachHangPhieuGiamGia , Long> {

    Page<KhachHangPhieuGiamGia> findByPhieuGiamGiaId(Long id , Pageable pageable);
}

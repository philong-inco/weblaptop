package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface KhachHangPhieuGiamGiaRepository extends JpaRepository<KhachHangPhieuGiamGia , Long> {

    Page<KhachHangPhieuGiamGia> findByPhieuGiamGiaId(Long id , Pageable pageable);

    Set<KhachHangPhieuGiamGia> findByPhieuGiamGiaId(Long idPgg);
    Optional<KhachHangPhieuGiamGia>  findKhachHangPhieuGiamGiaByPhieuGiamGiaIdAndKhachHangId(Long idPgg, Long idKhach);
}

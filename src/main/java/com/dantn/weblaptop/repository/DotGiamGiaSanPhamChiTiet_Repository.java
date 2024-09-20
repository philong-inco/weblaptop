package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DotGiamGiaSanPhamChiTiet_Repository extends JpaRepository<DotGiamGiaSanPhamChiTiet, Long> {
    Set<DotGiamGiaSanPhamChiTiet> findByDotGiamGiaId(Long idDgg);
}

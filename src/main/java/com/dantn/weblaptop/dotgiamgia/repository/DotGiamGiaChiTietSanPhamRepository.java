package com.dantn.weblaptop.dotgiamgia.repository;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DotGiamGiaChiTietSanPhamRepository extends JpaRepository<DotGiamGiaSanPhamChiTiet, Long> {
    @Query("select sp from SanPhamChiTiet as sp WHERE sp.id = :id")
    SanPhamChiTiet findSanPhamChiTietById(@Param("id") Long id);
//
//    @Query("SELECT d FROM DotGiamGia d LEFT JOIN FETCH d.dotGiamGiaSanPhamChiTiets")
//    List<DotGiamGia> findAllWithDetails();
}

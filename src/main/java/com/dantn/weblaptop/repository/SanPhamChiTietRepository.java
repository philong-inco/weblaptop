package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {

    @Query("SELECT s FROM SanPhamChiTiet s")
    Page<SanPhamChiTiet> getAllPage(Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s")
    List<SanPhamChiTiet> getAllList();

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.trangThai = :status")
    Page<SanPhamChiTiet> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.trangThai = :status")
    List<SanPhamChiTiet> findByStatusList(Integer status);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE LOWER(s.sanPham.ten) LIKE %:name%")
    Page<SanPhamChiTiet> findByProductName(@Param("name") String productName, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE LOWER(s.ma) = :code")
    List<SanPhamChiTiet> isExistCode(@Param("code") String code);


}

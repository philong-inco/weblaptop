package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.khachhang.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaChi_Repository extends JpaRepository<DiaChi, Long> {
    @Query(value = "SELECT dc FROM DiaChi dc WHERE dc.id = :id")
    Optional<DiaChi> findById(@Param("id") Long id);

    @Query(value = "UPDATE DiaChi dc SET dc.trangThai = :trangThai WHERE dc.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT v FROM DiaChi v WHERE v.khachHang.id = :idKhachHang")
    List<DiaChi> findByKhachHangId(@Param("idKhachHang") Long idKhachHang);

    @Query("SELECT v FROM DiaChi v WHERE v.khachHang.id = :idKhachHang AND v.loaiDiaChi = 1")
    Optional<DiaChi> findLocationDefaultByKhachHangId(@Param("idKhachHang") Long idKhachHang);

    @Modifying
    @Transactional
    @Query("UPDATE DiaChi dc SET dc.loaiDiaChi = (case when dc.id = :id then 1 when dc.id != :id then 0 END ) WHERE dc.khachHang.id = :idKhachHang")
    void defaultDiaChi(@Param("id") Long id, @Param("idKhachHang") Long idKhachHang);

    @Modifying
    @Transactional
    @Query("UPDATE DiaChi dc SET dc.loaiDiaChi = 0 WHERE dc.khachHang.id = :idKhachHang")
    void unDefaultDiaChi(@Param("id") Long id, @Param("idKhachHang") Long idKhachHang);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DiaChi dc WHERE dc.id = :id")
    void deleteByKhachHangId(@Param("id") Long id);
}

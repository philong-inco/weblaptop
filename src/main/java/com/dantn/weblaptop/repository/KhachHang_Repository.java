package com.dantn.weblaptop.repository;


import com.dantn.weblaptop.entity.khachhang.KhachHang;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface KhachHang_Repository extends JpaRepository<KhachHang, Integer> {
    @Query("SELECT kh FROM KhachHang kh WHERE " +
            "(:search is null or kh.ma LIKE %:search% or kh.ten LIKE %:search% or kh.email like %:search% or kh.sdt like %:search%)")
    Page<KhachHang> pageSearch(Pageable pageable, @Param("search") String search);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.sdt = :sdt")
    KhachHang findKhachHangBySdt(@Param("sdt") String sdt);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.email = :email")
    KhachHang findKhachHangByEmail(@Param("email") String email);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.id = :id")
    KhachHang findKhachHangById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE KhachHang kh SET kh.trangThai = :trangThaiKhachHang WHERE kh.id = :id")
    void removeOrRevert(@Param("trangThaiKhachHang") Integer trangThaiKhachHang, @Param("id") Long id);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.id = :id")
    Optional<KhachHang> findById(Long id);

    @Query(value = "UPDATE KhachHang kh SET kh.hinhAnh = :image WHERE kh.email = :email")
    void updateImageKhachHang(String image, String email);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.gioiTinh = :gioiTinh")
    Page<KhachHang> pageSearchGioiTinh(Pageable pageable, @Param("gioiTinh") Integer gioiTinh);

    @Query(value = "SELECT kh FROM KhachHang kh WHERE kh.hangKhachHang = :hangKhachHang")
    Page<KhachHang> pageSearchHangKhachHang(Pageable pageable, @Param("hangKhachHang") Integer hangKhachHang);
}

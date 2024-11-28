package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {

    Optional<GioHang> findByKhachHangId(Long khachHangId);

    Optional<GioHang> findBySessionId(String sessionId);

    @Query(value = "" +
            "SELECT count(ghct.id) FROM gio_hang as gh\n" +
            "join khach_hang as kh on kh.id = gh.id_khach_hang \n" +
            "join gio_hang_chi_tiet as ghct on ghct.gio_hang_id = gh.id\n" +
            "where kh.id= :idKhachHang", nativeQuery = true)
    Integer quantityInCart(@Param("idKhachHang") Long idKhachHang);

    @Query(value = "" +
            "SELECT count(ghct.id) FROM gio_hang as gh\n" +
            "join gio_hang_chi_tiet as ghct on ghct.gio_hang_id = gh.id\n" +
            "where gh.session_id= :sessionId", nativeQuery = true)
    Integer quantityInCartBySessionId(@Param("sessionId") String sessionId);
}

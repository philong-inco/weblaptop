package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DotGiamGiaRepository extends JpaRepository<DotGiamGia, Long>, JpaSpecificationExecutor<DotGiamGia> {
    @Query("select d from DotGiamGia d where d.id != :idRequest and (d.ten = :tenRequest or d.ma = :maRequest)")
    DotGiamGia findByNameOrMaExcludingId(@Param("idRequest") Long idRequest, @Param("tenRequest") String tenRequest, @Param("maRequest") String maRequest);

    @Query("select  d from  DotGiamGia  d where d.ten =:tenRequest or d.ma =:maRequest")
    DotGiamGia findbyNameAndMa(@Param("tenRequest") String tenRequest, @Param("maRequest") String maRequest);

    @Query("select d from DotGiamGia d")
    Page<DotGiamGia> finAllDotGiamGia(Pageable pageable);

    @Query("SELECT d FROM DotGiamGia d WHERE "
           + "(:tenOrMa IS NULL OR d.ten LIKE %:tenOrMa% OR d.ma LIKE %:tenOrMa%) "
           + "AND (:giaTri IS NULL OR d.giaTriGiam = :giaTri) "
           + "AND (:trangThai IS NULL OR d.trangThai = :trangThai) "
           + "AND (:thoiGianBatDau IS NULL OR d.thoiGianBatDau >= :thoiGianBatDau) "
           + "AND (:thoiGianKetThuc IS NULL OR d.thoiGianKetthuc <= :thoiGianKetThuc) "
           + "ORDER BY d.thoiGianBatDau DESC")  // Thêm ORDER BY để sắp xếp
    Page<DotGiamGia> filterAllDiscount(Pageable pageable, @Param("tenOrMa") String tenOrMa, @Param("giaTri") Integer giaTri, @Param("trangThai") Integer trangThai, @Param("thoiGianBatDau") LocalDateTime thoiGianBatDau, @Param("thoiGianKetThuc") LocalDateTime thoiGianKetThuc);

    @Query("SELECT sp FROM SanPhamChiTiet sp WHERE (:idSanPham IS NULL OR sp.sanPham.id IN :idSanPham)")
    Page<SanPhamChiTiet> timKiemSanPhamChiTietTheoIdSanPham(@Param("idSanPham") List<Long> idSanPham, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE DotGiamGia dgg SET dgg.trangThai = 0 WHERE dgg.id = :id")
    void updateStatusDGG(@Param("id") Long id);

    Boolean existsByMa(String ma);

    @Query("SELECT dgg FROM DotGiamGia dgg JOIN dgg.dotGiamGiaSanPhamChiTiets dggct WHERE dggct.sanPhamChiTiet.id = :idSPCT AND dgg.trangThai = 1")
    public List<DotGiamGia> getDotGiamGiaBySPCTId(@Param("idSPCT")Long idSPCT);
}

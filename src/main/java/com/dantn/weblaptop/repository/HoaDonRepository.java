package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.HoaDonSummaryDTO;
import com.dantn.weblaptop.dto.TrangThaiHoaDon_Dto;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long>, JpaSpecificationExecutor<HoaDon> {
    Optional<HoaDon> findHoaDonByMa(String ma);

    List<HoaDon> findByTrangThaiAndLoaiHoaDon(HoaDonStatus status, Integer type);

    Page<HoaDon> findByTrangThaiAndLoaiHoaDon(HoaDonStatus status, Integer type, Pageable pageable);

    Optional<HoaDon> findByIdAndTrangThai(Long id, HoaDonStatus status);

    @Query(value = "SELECT ma FROM hoa_don WHERE trang_thai IN (0)", nativeQuery = true)
    List<String> getAllByStatus();

    @Query(value = "SELECT COUNT(hd.id) as TongSoHoaDon FROM HoaDon hd WHERE hd.ngayThanhToan BETWEEN :startDate AND :endDate")
    Long countBillByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(hd.tongSanPham) AS TongSanPham FROM HoaDon hd WHERE hd.ngayThanhToan BETWEEN :startDate AND :endDate")
    Long sumProductSoldOut(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime);

    @Modifying
    @Transactional
    @Query(value = "" +
            "UPDATE hoa_don hd\n" +
            "LEFT JOIN (\n" +
            "    SELECT sndb.hoa_don_id, IFNULL(SUM(spct.gia_ban), 0) AS tong_tien\n" +
            "    FROM serial_number_da_ban sndb\n" +
            "    JOIN serial_number sn ON sndb.serial_number_id = sn.id\n" +
            "    JOIN san_pham_chi_tiet spct ON sn.san_pham_chi_tiet_id = spct.id\n" +
            "    WHERE sn.trang_thai = 0 \n" +
            "    GROUP BY sndb.hoa_don_id\n" +
            ") AS subquery ON hd.id = subquery.hoa_don_id\n" +
            "SET hd.tong_tien_ban_dau = IFNULL(subquery.tong_tien, 0) \n" +
            " WHERE hd.ma != :billCode", nativeQuery = true)
    void updateTotalMoneyByBillCode(@Param("billCode") String billCode);

    @Modifying
    @Transactional
    @Query(value = "" +
            "UPDATE hoa_don hd\n" +
            "LEFT JOIN (\n" +
            "    SELECT pgg.*, \n" +
            "        CASE \n" +
            "            WHEN pgg.loai_giam_gia = 1 THEN (pgg.gia_tri_giam_gia * 100000 / 100)  \n" +
            "            WHEN pgg.loai_giam_gia = 2 THEN pgg.gia_tri_giam_gia     \n" +
            "            ELSE 0 \n" +
            "        END AS discountValue\n" +
            "    FROM phieu_giam_gia pgg\n" +
            ") AS subquery ON hd.phieu_giam_gia_id = subquery.id\n" +
            "SET hd.phieu_giam_gia_id = NULL \n" +
            "WHERE hd.tong_tien_ban_dau <= IFNULL(subquery.discountValue, 0) \n" +
            "AND hd.phieu_giam_gia_id IS NOT NULL; ", nativeQuery = true)
    void deleteCouponInBill();

    @Query(value = "SELECT sum(tong_tien_phai_tra) FROM hoa_don where khach_hang_id =:idKh and trang_thai = 6", nativeQuery = true)
    Optional<BigDecimal> tongTienDaChiCuaKhachHang(@Param("idKh") Long idKh);

    List<HoaDon> findAllByTrangThai(HoaDonStatus status);

    Optional<HoaDon> findByMaAndSdt(String ma, String sdt);

    List<HoaDon> findAllByTrangThaiAndKhachHangId(HoaDonStatus trangThai, Long khachHangId);

    List<HoaDon> findAllByKhachHangId(Long khachHangId);

    @Query(value = "SELECT SUM(hd.tongTienPhaiTra) FROM HoaDon hd " +
            "WHERE hd.ngayThanhToan >= :startDate " +
            "AND hd.ngayThanhToan <= :endDate")
    BigDecimal totalPriceByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT SUM(hd.tongTienPhaiTra) FROM HoaDon hd " +
            "WHERE hd.ngayThanhToan >= CURRENT_DATE " +
            "AND hd.trangThai = 6")
    BigDecimal totalPriceByDateNow();

//    @Query("SELECT new com.dantn.weblaptop.dto.HoaDonSummaryDTO(COUNT(hd) AS tongHoaDon, SUM(hd.tongSanPham) AS tongSanPham) " +
//            "FROM HoaDon hd " +
//            "WHERE hd.ngayThanhToan >= :startDate " +
//            "AND hd.ngayThanhToan <= :endDate " +
//            "AND hd.trangThai = 9")
//    HoaDonSummaryDTO getInvoiceCountAndTotalProducts(@Param("startDate") LocalDateTime startDate,
//                                                     @Param("endDate") LocalDateTime endDate);

    @Query("SELECT FUNCTION('DATE', hd.ngayThanhToan) AS ngayThanhToan, COUNT(hd) AS soHoaDon, SUM(hd.tongSanPham) AS tongSanPham " +
            "FROM HoaDon hd " +
            "WHERE hd.ngayThanhToan >= :startDateTime " +
            "AND hd.ngayThanhToan <= :endDateTime " +
            "GROUP BY FUNCTION('DATE', hd.ngayThanhToan)")
    List<Object[]> countInvoicesAndSumProductsByDate(@Param("startDateTime") LocalDateTime startDateTime,
                                                     @Param("endDateTime") LocalDateTime endDateTime);

    @Query(value = "SELECT COUNT(*) AS SoLuong, hd.trang_thai AS trangThai, " +
            "(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM hoa_don WHERE ngay_tao BETWEEN :startDateTime AND :endDateTime)) AS TiLePhanTram " +
            "FROM hoa_don hd " +
            "WHERE hd.ngay_tao BETWEEN :startDateTime AND :endDateTime " +
            "GROUP BY hd.trang_thai " +
            "ORDER BY hd.trang_thai",
            nativeQuery = true)
    List<Object[]> totalCalculateBillPercentageByDate(@Param("startDateTime") Long startDateTime,
                                                      @Param("endDateTime") Long endDateTime);

    @Query(value = "select * from hoa_don as hd where hd.trang_thai in (0,11)", nativeQuery = true)
    List<HoaDon> listBillClear();
}


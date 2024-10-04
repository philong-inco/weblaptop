package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Query(value = "SELECT COUNT(hd) FROM HoaDon hd WHERE  hd.ngayTao BETWEEN :startDate AND :endDate")
    Long countBillByDate(@Param("startDate") Long startDate, @Param("endDate") Long endDate);

    @Query(value = "SELECT SUM(hd.tongTienPhaiTra) FROM HoaDon hd WHERE  hd.ngayTao BETWEEN :startDate AND :endDate")
    BigDecimal totalPriceInBillByDate(@Param("startDate") Long startDate, @Param("endDate") Long endDate);

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

}

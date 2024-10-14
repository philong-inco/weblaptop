package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
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
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuGiamGiaRepo extends JpaRepository<PhieuGiamGia, Long>, JpaSpecificationExecutor<PhieuGiamGia> {
    Boolean existsByMa(String ma);

    Optional<PhieuGiamGia> findByMa(String ma);

    //    Lấy pgg dk với khách lẻ
    @Query(value = "" +
            "select pgg.* from phieu_giam_gia as pgg\n" +
            "where pgg.trang_thai =1 \n" +
            "and pgg.pham_vi_ap_dung =1 \n" +
            "and pgg.so_luong > 0 \n" +
            "and pgg.gia_tri_don_toi_thieu <= :totalAmount"
            , nativeQuery = true)
    List<PhieuGiamGia> getAllByTotalAmount(@Param("totalAmount") BigDecimal totalAmount);

    // check add tay khách lẻ
    @Query(value = "" +
            "select pgg.* from phieu_giam_gia as pgg\n" +
            "where pgg.trang_thai =1 \n" +
            "and pgg.pham_vi_ap_dung =1 \n" +
            "and pgg.so_luong > 0 \n" +
            "and pgg.gia_tri_don_toi_thieu <= :totalAmount \n" +
            " AND  pgg.ma = :couponCode "
            , nativeQuery = true)
    Optional<PhieuGiamGia> getByTotalAmountAndCouponCode(
            @Param("totalAmount") BigDecimal totalAmount,
            @Param("couponCode") String couponCode);

    // Lấy phiếu tốt nhất cho khách lẻ
    @Query(value = "" +
            "SELECT pgg.*, " +
            "CASE " +
            "    WHEN " +
            "        CASE " +
            "            WHEN pgg.loai_giam_gia = 1 THEN pgg.gia_tri_giam_gia * :totalAmount / 100 " +
            "            WHEN pgg.loai_giam_gia = 2 THEN pgg.gia_tri_giam_gia " +
            "            ELSE 0 " +
            "        END > pgg.giam_toi_da " +
            "    THEN pgg.giam_toi_da " +
            "    ELSE " +
            "        CASE " +
            "            WHEN pgg.loai_giam_gia = 1 THEN pgg.gia_tri_giam_gia * :totalAmount / 100 " +
            "            WHEN pgg.loai_giam_gia = 2 THEN pgg.gia_tri_giam_gia " +
            "            ELSE 0 " +
            "        END " +
            "END AS discountValue " +
            "FROM phieu_giam_gia pgg " +
            "WHERE pgg.trang_thai = 1 " +
            "    AND pgg.pham_vi_ap_dung = 1 " +
            "    AND pgg.so_luong > 0 " +
            "    AND pgg.gia_tri_don_toi_thieu <= :totalAmount " +
            "ORDER BY discountValue DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<PhieuGiamGia> getHighestDiscountVoucherByTotalAmount(@Param("totalAmount") BigDecimal totalAmount);

    // Lấy pgg với khách hàng
    @Query(value = "" +
            "SELECT pgg.* \n" +
            "FROM phieu_giam_gia AS pgg\n" +
            "LEFT JOIN khach_hang_phieu_giam_gia AS khpgg ON khpgg.phieu_giam_gia_id = pgg.id \n" +
            "LEFT JOIN khach_hang AS kh ON khpgg.khach_hang_id = kh.id \n" +
            "WHERE pgg.trang_thai = 1 \n" +
            "  AND (khpgg.trang_thai = 0 OR khpgg.khach_hang_id IS NULL) \n" +
            "  AND pgg.so_luong > 0 \n" +
            "  AND (kh.id = :customerId OR kh.id IS NULL) \n" +
            "  AND pgg.gia_tri_don_toi_thieu <= :totalAmount",
            nativeQuery = true)
    List<PhieuGiamGia> getAllByTotalAmountAndCustomer(
            @Param("totalAmount") BigDecimal totalAmount, @Param("customerId") Long customerId);

    // check pgg có app cho kh ko
    @Query(value = "" +
            "SELECT pgg.* \n" +
            "FROM phieu_giam_gia AS pgg \n" +
            "LEFT JOIN khach_hang_phieu_giam_gia AS khpgg ON khpgg.phieu_giam_gia_id = pgg.id \n" +
            "LEFT JOIN khach_hang AS kh ON khpgg.khach_hang_id = kh.id \n" +
            "WHERE pgg.trang_thai = 1 \n" +
            "  AND (khpgg.trang_thai = 0 OR khpgg.khach_hang_id IS NULL) \n" +
            "  AND pgg.so_luong > 0 \n" +
            "  AND (kh.id = :customerId OR kh.id IS NULL) \n" +
            "  AND pgg.gia_tri_don_toi_thieu <= :totalAmount \n" +
            "  AND pgg.id = :couponId \n" +
            "LIMIT 1",
            nativeQuery = true)
    Optional<PhieuGiamGia> getVoucherByTotalAmountCustomerAndCoupon(
            @Param("totalAmount") BigDecimal totalAmount,
            @Param("customerId") Long customerId,
            @Param("couponId") Long couponId);


    // check add tay khách hệ thống
    @Query(value = "" +
            "SELECT pgg.* \n" +
            "FROM phieu_giam_gia AS pgg\n" +
            "LEFT JOIN khach_hang_phieu_giam_gia AS khpgg ON khpgg.phieu_giam_gia_id = pgg.id \n" +
            "LEFT JOIN khach_hang AS kh ON khpgg.khach_hang_id = kh.id \n" +
            "WHERE pgg.trang_thai = 1 \n" +
            "  AND (khpgg.trang_thai = 0 OR khpgg.khach_hang_id IS NULL) \n" +
            "  AND pgg.so_luong > 0 \n" +
            "  AND (kh.id = :customerId OR kh.id IS NULL) \n" +
            "  AND pgg.gia_tri_don_toi_thieu <= :totalAmount \n" +
            "   AND  pgg.ma = :couponCode ",
            nativeQuery = true)
    Optional<PhieuGiamGia> getAllByTotalAmountAndCustomerAndCouponCode(
            @Param("totalAmount") BigDecimal totalAmount,
            @Param("customerId") Long customerId,
            @Param("couponCode") String couponCode
    );

    // phiếu tốt với khánh hệ thống
    @Query(value = "" +
            "SELECT pgg.*, \n" +
            "    CASE \n" +
            "        WHEN pgg.loai_giam_gia = 1 THEN \n" +
            "            CASE \n" +
            "                WHEN (pgg.gia_tri_giam_gia * :totalAmount / 100) > pgg.giam_toi_da THEN pgg.giam_toi_da \n" +
            "                ELSE (pgg.gia_tri_giam_gia * :totalAmount / 100) \n" +
            "            END \n" +
            "        WHEN pgg.loai_giam_gia = 2 THEN \n" +
            "            CASE \n" +
            "                WHEN pgg.gia_tri_giam_gia > pgg.giam_toi_da THEN pgg.giam_toi_da \n" +
            "                ELSE pgg.gia_tri_giam_gia \n" +
            "            END \n" +
            "        ELSE 0 \n" +
            "    END AS discountValue \n" +
            "FROM phieu_giam_gia AS pgg\n" +
            "LEFT JOIN khach_hang_phieu_giam_gia AS khpgg ON khpgg.phieu_giam_gia_id = pgg.id\n" +
            "LEFT JOIN khach_hang AS kh ON khpgg.khach_hang_id = kh.id\n" +
            "WHERE pgg.trang_thai = 1  \n" +
            "  AND pgg.so_luong > 0\n" +
            "  AND (khpgg.trang_thai = 0 OR khpgg.khach_hang_id IS NULL) \n" +
            "  AND (kh.id = :customerId OR kh.id IS NULL) \n" +
            "  AND pgg.gia_tri_don_toi_thieu <= :totalAmount\n" +
            "ORDER BY discountValue DESC\n" +
            "LIMIT 1;",
            nativeQuery = true)
    Optional<PhieuGiamGia> getHighestDiscountVoucherByTotalAmountAndCustomer(
            @Param("totalAmount") BigDecimal totalAmount,
            @Param("customerId") Long customerId);


    // lấy giá trị giảm quy đổi
    @Query(value = "SELECT " +
            "CASE " +
            "    WHEN discountValue > giam_toi_da THEN giam_toi_da " +
            "    ELSE discountValue " +
            "END AS discountValue " +
            "FROM ( " +
            "    SELECT " +
            "        LEAST( " +
            "            CASE " +
            "                WHEN loai_giam_gia = 1 THEN (gia_tri_giam_gia * :amount / 100)  " +
            "                WHEN loai_giam_gia = 2 THEN gia_tri_giam_gia  " +
            "                ELSE 0 " +
            "            END, " +
            "            giam_toi_da " +
            "        ) AS discountValue, " +
            "        giam_toi_da " +
            "    FROM phieu_giam_gia " +
            "    WHERE trang_thai = 1 " +
            "      AND so_luong > 0 " +
            "      AND gia_tri_don_toi_thieu <= :amount " +
            "      AND id = :id " +
            ") AS discountCalculation " +
            "ORDER BY discountValue DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<BigDecimal> findDiscountValue(@Param("amount") BigDecimal amount, @Param("id") Long id);


    @Query(value = "SELECT pgg FROM PhieuGiamGia  pgg WHERE pgg.trangThai = :trangThai")
    Page<PhieuGiamGia> getPhieuGiamGiaPageActive(Pageable pageable, @Param("trangThai") Integer trangThai);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PhieuGiamGia pgg  SET pgg.trangThai = 1 WHERE pgg.id = :id")
    void changeStatusPhieuGiamGiaPause(Long id);


}
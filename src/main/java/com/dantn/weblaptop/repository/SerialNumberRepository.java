package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SerialNumber;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long> {


    @Query("SELECT s FROM SerialNumber s")
    List<SerialNumber> getAllList();

    @Query("SELECT s FROM SerialNumber s")
    Page<SerialNumber> getAllPage(Pageable pageable);

    @Query("SELECT s FROM SerialNumber s WHERE lower(s.ma) = :ma")
    List<SerialNumber> existByMaForAdd(@Param("ma") String ma);

    @Query("SELECT s FROM SerialNumber s WHERE lower(s.ma) = :ma AND s.id <> :id")
    List<SerialNumber> existByMaForUpdate(@Param("ma") String ma, @Param("id") Long id);

    @Query("SELECT s FROM SerialNumber s WHERE lower(s.ma) = :ma ")
    SerialNumber findByMa(@Param("ma") String ma);

    @Query("DELETE FROM SerialNumber s WHERE s.sanPhamChiTiet.id = :idSPCT AND s.trangThai = 1")
    void deleteAllByIdSPCT(@Param("idSPCT") Long idSPCT);

    @Query("UPDATE SerialNumber s SET s.trangThai = 1 WHERE s.id = :id")
    void changeStatusToSeriNumberDaBan(Long id);

    @Query("SELECT s from  SerialNumber s WHERE s.sanPhamChiTiet.sanPham.id = :idSP AND s.trangThai = 0")
    List<SerialNumber> findSerialNumberBySanPhamId(@Param("idSP") Long idSP);

    @Query("SELECT s FROM SerialNumber s WHERE s.sanPhamChiTiet.id = :id")
    List<SerialNumber> findBySanPhamChiTietId(@Param("id") Long id);

    @Query("SELECT s FROM SerialNumber s WHERE s.sanPhamChiTiet.id = :id AND s.trangThai = 0")
    List<SerialNumber> findBySanPhamChiTietIdActive(@Param("id") Long id);


    List<SerialNumber> findBySanPhamChiTietIdAndTrangThai(Long productDetailId, Integer status);

    Page<SerialNumber> findBySanPhamChiTietIdAndTrangThai(Long productDetailId, Integer status, Pageable pageable);

    Page<SerialNumber> findBySanPhamChiTietId(Long productDetailId, Pageable pageable);

    @Query("SELECT sn FROM " +
            "SerialNumber sn " +
            "WHERE (:codeSerial = '' " +
            "OR sn.ma LIKE %:codeSerial%) AND sn.trangThai = 0 " +
            "AND sn.sanPhamChiTiet.id = :productDetailId")
    Page<SerialNumber> findByMaContainingAndSanPhamChiTietId(String codeSerial , Long productDetailId, Pageable pageable);

    @Query(value = "SELECT sn FROM SerialNumber sn " +
            "LEFT JOIN SerialNumberDaBan sndb ON sn.id = sndb.serialNumber.id " +
            "AND sndb.hoaDon.id IN (SELECT hd.id FROM HoaDon hd WHERE hd.ma = :maHoaDon) " +  // Di chuyển vào đây
            "WHERE (sn.trangThai = 0 OR (sn.trangThai = 1 AND sndb.serialNumber.id IS NOT NULL)) " +
            "AND (sn.trangThai = 0 OR sndb.hoaDon.id IS NOT NULL) " +
            "AND sn.sanPhamChiTiet.id = :sanPhamChiTietId " +
            "AND sn.ma LIKE %:maSerial% "+
            "ORDER BY CASE WHEN sndb.serialNumber.id IS NOT NULL THEN 0 ELSE 1 END, sn.trangThai DESC")

//            +
//            "ORDER BY sn.trangThai ASC")
    Page<SerialNumber> findSerialNumbers(@Param("maHoaDon") String maHoaDon,
                                         @Param("sanPhamChiTietId") Long sanPhamChiTietId,
                                         @Param("maSerial") String maSerial,
                                         Pageable pageable);

    @Query(value = "SELECT sn FROM SerialNumber sn " +
            "JOIN SanPhamChiTiet spct ON spct.id = sn.sanPhamChiTiet.id " +
            "LEFT JOIN SerialNumberDaBan sndb ON sn.id = sndb.serialNumber.id " +
            "AND sndb.hoaDon.id IN (SELECT hd.id FROM HoaDon hd WHERE hd.ma = :maHoaDon) " +
            "WHERE (sn.trangThai = 0 OR (sn.trangThai = 1 AND sndb.serialNumber.id IS NOT NULL)) " +
            "AND (sn.trangThai = 0 OR sndb.hoaDon.id IS NOT NULL) " +
            "AND spct.ma = :maSanPhamChiTIet " +
            "AND sn.ma LIKE %:maSerial% " +
            "ORDER BY CASE WHEN sndb.serialNumber.id IS NOT NULL THEN 0 ELSE 1 END, sn.trangThai DESC")

//            +
//            "ORDER BY sn.trangThai DESC")
    Page<SerialNumber> findSerialNumbersByProductCode(
            @Param("maHoaDon") String maHoaDon,
            @Param("maSanPhamChiTIet") String maSanPhamChiTIet,
            @Param("maSerial") String maSerial,
            Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE serial_number sn " +
            "SET sn.trang_thai = 1 " +
            "WHERE sn.id IN (:serialsId)", nativeQuery = true)
    void updateStatusByInIds(@Param("serialsId") List<Long> serialsId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE serial_number sn SET sn.trang_thai = :status WHERE sn.id IN (:ids)", nativeQuery = true)
    void updateStatusByIdsNative(@Param("status") Integer status, @Param("ids") List<Long> ids);

    @Query(value = "select sr.* from serial_number as sr \n" +
            "join serial_number_da_ban as srdb on sr.id =srdb.serial_number_id\n" +
            "where sr.trang_thai = :status  and srdb.hoa_don_id = :billId ",nativeQuery = true)
    List<SerialNumber> findSerialNumbersByDaBanByStatusAndBillId(@Param("status") Integer status, @Param("billId") Long billId);

    @Query(value = "select sr.* from serial_number as sr \n" +
            "join serial_number_da_ban as srdb on sr.id =srdb.serial_number_id\n" +
            "where sr.trang_thai = :status  and srdb.hoa_don_id = :billId and  sr.san_pham_chi_tiet_id = :productId ",nativeQuery = true)
    List<SerialNumber> findSerialNumbersByDaBanByStatusAndBillIdAndProductId(
            @Param("status") Integer status,
            @Param("billId") Long billId,
            @Param("productId") Long productId
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM SerialNumber s WHERE s.id = :id")
    void deleteByIdSeri(@Param(("id")) Long id);

    @Query(value = "" +
            "SELECT * FROM serial_number " +
            "WHERE san_pham_chi_tiet_id = :productDetailId " +
            "AND trang_thai = 0 LIMIT :limit", nativeQuery = true)
    List<SerialNumber> findBySanPhamChiTietIdAndTrangThaiWithLimit(
            @Param("productDetailId") Long productDetailId,
            @Param("limit") int limit);
    @Query(value = "" +
            "SELECT \n" +
            "COUNT(sr.id) \n" +
            "AS serial_count\n" +
            "FROM serial_number AS sr \n" +
            "WHERE sr.san_pham_chi_tiet_id = :productDetailId  AND sr.trang_thai = 0;", nativeQuery = true)
    Integer  getQuantitySerialIsActive(@Param("productDetailId") Long productDetailId);

    @Query(value = "select sr.* from serial_number as sr \n" +
            "join serial_number_da_ban as srdb on sr.id = srdb.serial_number_id\n" +
            "where srdb.hoa_don_id = :billId", nativeQuery = true)
    List<SerialNumber>  getListSerialInBill (@Param("billId") Long billId);

    @Query(value = "SELECT sr.* FROM serial_number as sr \n" +
            "join serial_number_da_ban as srdb on sr.id =srdb.serial_number_id\n" +
            "where srdb.hoa_don_id = :billId and sr.trang_thai =0", nativeQuery = true)
    List<SerialNumber> getListSerialInBillStatusIs0(@Param("billId") Long billId);
}

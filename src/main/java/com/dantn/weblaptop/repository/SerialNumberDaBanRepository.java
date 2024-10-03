package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse2;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SerialNumberDaBanRepository extends JpaRepository<SerialNumberDaBan, Long> {
    List<SerialNumberDaBan> findAllByHoaDonId(Long billId);


    List<SerialNumberDaBan> findAllByHoaDonMa(String code);

    @Query("SELECT sndb FROM SerialNumberDaBan sndb " +
            "WHERE sndb.serialNumber.id IN :serialNumberIds " +
            "AND sndb.hoaDon.ma = :billCode")
    List<SerialNumberDaBan> findAllByIdInAndHoaDonMa(@Param("serialNumberIds") List<Long> serialNumberIds,
                                                     @Param("billCode") String billCode);

    @Query(value = "SELECT sndb.serial_number_id FROM serial_number_da_ban sndb " +
            "JOIN hoa_don hd ON hd.id = sndb.hoa_don_id " +
            "WHERE hd.ma = :billCode", nativeQuery = true)
    List<Long> getAllSerialNumberInBillByBillCode(@Param("billCode") String billCode);

    @Query(value = "SELECT * FROM serial_number_da_ban WHERE serial_number_id IN :serialNumberIds", nativeQuery = true)
    List<SerialNumberDaBan> findAllBySerialNumberIdIn(@Param("serialNumberIds") List<Long> serialNumberIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM serial_number_da_ban WHERE serial_number_id IN :serialNumberIds", nativeQuery = true)
    void deleteBySerialNumberIds(@Param("serialNumberIds") List<Long> serialNumberIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM serial_number_da_ban sndb WHERE sndb.hoa_don_id != :billId AND sndb.serial_number_id IN (:serialNumberIds)", nativeQuery = true)
    void deleteAllNotBillId(@Param("billId") Long billId, @Param("serialNumberIds") List<Long> serialNumberIds);


    @Query(value = "" +
            "select  serial_number_id  FROM serial_number_da_ban sndb \n" +
            "join hoa_don as hd on hd.id = sndb.hoa_don_id\n" +
            "where sndb.hoa_don_id = :billId", nativeQuery = true)
    List<Long> getSerialNumberInBillId(@Param("billId") Long billId);
}

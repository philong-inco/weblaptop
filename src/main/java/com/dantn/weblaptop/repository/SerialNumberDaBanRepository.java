package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse2;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}

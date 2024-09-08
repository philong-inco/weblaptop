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

    @Query(value = "SELECT new com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse(" +
            "MAX(sndb.id), " +
            "hd.id, " +
            "sn.id, " +
            "spct.id, " +
            "sn.ma, " +
            "spct.ma, " +
            "sp.ten, " +
            "spct.giaBan, " +
            "COUNT(sndb.id)) " +
            "FROM SerialNumberDaBan sndb " +
            "JOIN SerialNumber sn ON sndb.serialNumber.id = sn.id " +
            "JOIN SanPhamChiTiet spct ON sn.sanPhamChiTiet.id = spct.id " +
            "JOIN SanPham sp ON spct.sanPham.id = sp.id " +
            "JOIN HoaDon hd ON sndb.hoaDon.id = hd.id " +
            "WHERE hd.ma = :code " +
            "GROUP BY hd.id, sn.id, sn.ma, sp.ten, spct.id, spct.ma, spct.giaBan")
    List<SerialNumberDaBanResponse> getAllByHoaDonMa(@Param("code") String code);
}

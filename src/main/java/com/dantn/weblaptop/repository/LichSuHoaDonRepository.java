package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Long> {
    Page<LichSuHoaDon> findAllByHoaDonId(Long billId, Pageable pageable);

    List<LichSuHoaDon> findAllByHoaDonId(Long billId);

    List<LichSuHoaDon> findAllByHoaDonMa(String code);

    @Query(value = "" +
            "SELECT * FROM " +
            "lich_su_hoa_don " +
            "WHERE hoa_don_id = :billId",
            nativeQuery = true)
    List<LichSuHoaDon> getAllByBillId(@Param("billId") Long billId);
}

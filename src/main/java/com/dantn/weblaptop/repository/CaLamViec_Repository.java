package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.LichLamViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaLamViec_Repository extends JpaRepository<CaLamViec, Long> {
    @Query(value = "SELECT lv FROM CaLamViec lv WHERE lv.id = :id")
    CaLamViec findByIdCaLamViec(@Param("id") Long id);
}

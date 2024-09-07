package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaLamViec_Repository extends JpaRepository<CaLamViec, Long> {
    @Query(value = "SELECT c FROM CaLamViec c WHERE c.id = :id")
    CaLamViec findByIdCaLamViec(@Param("id") Long id);
}

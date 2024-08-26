package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.LichLamViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LichLamViec_Repository extends JpaRepository<LichLamViec, Long> {
    @Query(value = "SELECT ll FROM LichLamViec ll WHERE ll.id = :id")
    Optional<LichLamViec> findById(@Param("id") Long id);

    @Query(value = "SELECT ll FROM LichLamViec ll WHERE ll.nhanVien.id = :idNhanVien")
    List<LichLamViec> findLichLamViecByIdNhanVien(@Param("idNhanVien") Long idNhanVien);

    @Transactional
    @Modifying
    @Query(value = "UPDATE LichLamViec ll SET ll.trangThai = 0 WHERE ll.id = :id")
    void deleteTrangThai(@Param("id") Long id);
}

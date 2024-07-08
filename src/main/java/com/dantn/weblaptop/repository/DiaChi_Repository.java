package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaChi_Repository extends JpaRepository<DiaChi, Integer> {
    @Query(value = "SELECT dc FROM DiaChi dc WHERE dc.id = :id")
    Optional<DiaChi> findById(@Param("id") Long id);

    @Query(value = "UPDATE DiaChi dc SET dc.trangThai = :trangThai WHERE dc.id = :id")
    void deleteById(@Param("id") Long id);

    @Query(value = "SELECT v FROM DiaChi v")
    Page<DiaChi> pageAll(Pageable pageable);
}
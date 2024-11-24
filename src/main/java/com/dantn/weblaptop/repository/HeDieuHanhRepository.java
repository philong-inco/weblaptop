package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeDieuHanhRepository extends IGenericsRepository<HeDieuHanh, Long> {
    @Override
    @Query("SELECT r FROM HeDieuHanh r")
    Page<HeDieuHanh> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM HeDieuHanh r")
    List<HeDieuHanh> getAllList();

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE r.trangThai = 1 ORDER BY r.ngayTao DESC")
    List<HeDieuHanh> getAllListActive();

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE r.trangThai = :status")
    Page<HeDieuHanh> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE r.trangThai = :status")
    List<HeDieuHanh> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<HeDieuHanh> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<HeDieuHanh> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE LOWER(r.ma) = :code")
    List<HeDieuHanh> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE LOWER(r.ten) = :name")
    List<HeDieuHanh> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM HeDieuHanh r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<HeDieuHanh> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

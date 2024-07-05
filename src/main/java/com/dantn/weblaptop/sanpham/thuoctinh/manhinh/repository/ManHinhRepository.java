package com.dantn.weblaptop.sanpham.thuoctinh.manhinh.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManHinhRepository extends IGenericsRepository<ManHinh, Long> {
    @Override
    @Query("SELECT r FROM ManHinh r")
    Page<ManHinh> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM ManHinh r")
    List<ManHinh> getAllList();

    @Override
    @Query("SELECT r FROM ManHinh r WHERE r.trangThai = :status")
    Page<ManHinh> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE r.trangThai = :status")
    List<ManHinh> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<ManHinh> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<ManHinh> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE LOWER(r.ma) = :code")
    List<ManHinh> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE LOWER(r.ten) = :name")
    List<ManHinh> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM ManHinh r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<ManHinh> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

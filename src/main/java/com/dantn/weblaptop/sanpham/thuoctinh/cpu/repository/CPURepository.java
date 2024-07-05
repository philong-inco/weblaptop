package com.dantn.weblaptop.sanpham.thuoctinh.cpu.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CPURepository extends IGenericsRepository<CPU, Long> {
    @Override
    @Query("SELECT r FROM CPU r")
    Page<CPU> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM CPU r")
    List<CPU> getAllList();

    @Override
    @Query("SELECT r FROM CPU r WHERE r.trangThai = :status")
    Page<CPU> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM CPU r WHERE r.trangThai = :status")
    List<CPU> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM CPU r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<CPU> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM CPU r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<CPU> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM CPU r WHERE LOWER(r.ma) = :code")
    List<CPU> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM CPU r WHERE LOWER(r.ten) = :name")
    List<CPU> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM CPU r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<CPU> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

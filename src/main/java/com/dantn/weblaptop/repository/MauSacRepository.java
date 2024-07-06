package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepository extends IGenericsRepository<MauSac, Long> {
    @Override
    @Query("SELECT r FROM MauSac r")
    Page<MauSac> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM MauSac r")
    List<MauSac> getAllList();

    @Override
    @Query("SELECT r FROM MauSac r WHERE r.trangThai = :status")
    Page<MauSac> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM MauSac r WHERE r.trangThai = :status")
    List<MauSac> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM MauSac r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<MauSac> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM MauSac r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<MauSac> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM MauSac r WHERE LOWER(r.ma) = :code")
    List<MauSac> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM MauSac r WHERE LOWER(r.ten) = :name")
    List<MauSac> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM MauSac r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<MauSac> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

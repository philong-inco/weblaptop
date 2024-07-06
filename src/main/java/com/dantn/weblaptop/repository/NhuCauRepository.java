package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhuCauRepository extends IGenericsRepository<NhuCau, Long> {
    @Override
    @Query("SELECT r FROM NhuCau r")
    Page<NhuCau> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM NhuCau r")
    List<NhuCau> getAllList();

    @Override
    @Query("SELECT r FROM NhuCau r WHERE r.trangThai = :status")
    Page<NhuCau> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE r.trangThai = :status")
    List<NhuCau> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<NhuCau> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<NhuCau> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE LOWER(r.ma) = :code")
    List<NhuCau> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE LOWER(r.ten) = :name")
    List<NhuCau> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM NhuCau r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<NhuCau> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

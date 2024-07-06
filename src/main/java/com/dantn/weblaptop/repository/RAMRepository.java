package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RAMRepository extends IGenericsRepository<RAM, Long> {
    @Override
    @Query("SELECT r FROM RAM r")
    Page<RAM> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM RAM r")
    List<RAM> getAllList();

    @Override
    @Query("SELECT r FROM RAM r WHERE r.trangThai = :status")
    Page<RAM> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM RAM r WHERE r.trangThai = :status")
    List<RAM> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM RAM r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<RAM> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM RAM r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<RAM> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM RAM r WHERE LOWER(r.ma) = :code")
    List<RAM> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM RAM r WHERE LOWER(r.ten) = :name")
    List<RAM> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM RAM r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<RAM> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

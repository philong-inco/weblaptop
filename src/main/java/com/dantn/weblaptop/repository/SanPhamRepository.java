package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends IGenericsRepository<SanPham, Long> {

    @Override
    @Query("SELECT r FROM SanPham r")
    Page<SanPham> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM SanPham r")
    List<SanPham> getAllList();

    @Override
    @Query("SELECT r FROM SanPham r WHERE r.trangThai = :status")
    Page<SanPham> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM SanPham r WHERE r.trangThai = :status")
    List<SanPham> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM SanPham r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<SanPham> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM SanPham r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<SanPham> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM SanPham r WHERE LOWER(r.ma) = :code")
    List<SanPham> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM SanPham r WHERE LOWER(r.ten) = :name")
    List<SanPham> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM SanPham r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<SanPham> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

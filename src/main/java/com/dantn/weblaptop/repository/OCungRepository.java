package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OCungRepository extends IGenericsRepository<OCung, Long> {
    @Override
    @Query("SELECT r FROM OCung r")
    Page<OCung> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM OCung r")
    List<OCung> getAllList();

    @Override
    @Query("SELECT r FROM OCung r WHERE r.trangThai = 1")
    List<OCung> getAllListActive();

    @Override
    @Query("SELECT r FROM OCung r WHERE r.trangThai = :status")
    Page<OCung> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM OCung r WHERE r.trangThai = :status")
    List<OCung> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM OCung r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<OCung> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM OCung r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<OCung> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM OCung r WHERE LOWER(r.ma) = :code")
    List<OCung> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM OCung r WHERE LOWER(r.ten) = :name")
    List<OCung> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM OCung r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<OCung> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

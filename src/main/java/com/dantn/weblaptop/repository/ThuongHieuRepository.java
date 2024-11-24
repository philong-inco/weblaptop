package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends IGenericsRepository<ThuongHieu, Long> {
    @Override
    @Query("SELECT r FROM ThuongHieu r")
    Page<ThuongHieu> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM ThuongHieu r")
    List<ThuongHieu> getAllList();

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE r.trangThai = 1 ORDER BY r.ngayTao DESC")
    List<ThuongHieu> getAllListActive();

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE r.trangThai = :status")
    Page<ThuongHieu> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE r.trangThai = :status")
    List<ThuongHieu> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<ThuongHieu> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<ThuongHieu> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE LOWER(r.ma) = :code")
    List<ThuongHieu> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE LOWER(r.ten) = :name")
    List<ThuongHieu> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM ThuongHieu r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<ThuongHieu> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

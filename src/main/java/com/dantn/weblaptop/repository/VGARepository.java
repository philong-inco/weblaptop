package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VGARepository extends IGenericsRepository<VGA, Long> {
    @Override
    @Query("SELECT r FROM VGA r")
    Page<VGA> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM VGA r")
    List<VGA> getAllList();

    @Override
    @Query("SELECT r FROM VGA r WHERE r.trangThai = 1 ORDER BY r.ngayTao DESC")
    List<VGA> getAllListActive();

    @Override
    @Query("SELECT r FROM VGA r WHERE r.trangThai = :status")
    Page<VGA> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM VGA r WHERE r.trangThai = :status")
    List<VGA> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM VGA r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<VGA> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM VGA r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<VGA> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM VGA r WHERE LOWER(r.ma) = :code")
    List<VGA> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM VGA r WHERE LOWER(r.ten) = :name")
    List<VGA> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM VGA r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<VGA> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

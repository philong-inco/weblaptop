package com.dantn.weblaptop.sanpham.thuoctinh.banphim.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.sanpham.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanPhimRepository extends IGenericsRepository<BanPhim, Long> {
    @Override
    @Query("SELECT r FROM BanPhim r")
    Page<BanPhim> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM BanPhim r")
    List<BanPhim> getAllList();

    @Override
    @Query("SELECT r FROM BanPhim r WHERE r.trangThai = :status")
    Page<BanPhim> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE r.trangThai = :status")
    List<BanPhim> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<BanPhim> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<BanPhim> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE LOWER(r.ma) = :code")
    List<BanPhim> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE LOWER(r.ten) = :name")
    List<BanPhim> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM BanPhim r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<BanPhim> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

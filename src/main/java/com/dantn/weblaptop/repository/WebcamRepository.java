package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.generics.IGenericsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebcamRepository extends IGenericsRepository<Webcam, Long> {
    @Override
    @Query("SELECT r FROM Webcam r")
    Page<Webcam> getAllPage(Pageable pageable);

    @Override
    @Query("SELECT r FROM Webcam r")
    List<Webcam> getAllList();

    @Override
    @Query("SELECT r FROM Webcam r WHERE r.trangThai = 1")
    List<Webcam> getAllListActive();

    @Override
    @Query("SELECT r FROM Webcam r WHERE r.trangThai = :status")
    Page<Webcam> findByStatusPage(@Param("status") Integer status, Pageable pageable);

    @Override
    @Query("SELECT r FROM Webcam r WHERE r.trangThai = :status")
    List<Webcam> findByStatusList(@Param("status") Integer status);

    @Override
    @Query("SELECT r FROM Webcam r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    Page<Webcam> findByCodeOrNamePage(@Param("key") String keyword, Pageable pageable);

    @Override
    @Query("SELECT r FROM Webcam r WHERE LOWER(r.ma) LIKE %:key% OR LOWER(r.ten) LIKE %:key%")
    List<Webcam> findByCodeOrNameList(@Param("key") String keyword);

    @Override
    @Query("SELECT r FROM Webcam r WHERE LOWER(r.ma) = :code")
    List<Webcam> isExistCode(@Param("code") String code);

    @Override
    @Query("SELECT r FROM Webcam r WHERE LOWER(r.ten) = :name")
    List<Webcam> isExistName(@Param("name") String name);

    @Override
    @Query("SELECT r FROM Webcam r WHERE LOWER(r.ten) = :name AND r.id <> :id")
    List<Webcam> isExistNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}

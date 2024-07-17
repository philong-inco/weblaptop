package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVien_Repositoy extends JpaRepository<NhanVien, Long> {
    @Query("SELECT nv FROM NhanVien nv WHERE " +
            "(:search is null or nv.ma LIKE %:search% or nv.ten LIKE %:search% or nv.email like %:search% or nv.sdt like %:search%)")
    Page<NhanVien> pageSearch(
            Pageable pageable
            , @Param("search") String search
    );

    @Query(value = "SELECT nv FROM NhanVien nv WHERE nv.trangThai = :trangThai")
    Page<NhanVien>  getNhanVienByTrangThai(Pageable pageable, Integer trangThai);

    @Query(value = "SELECT nv FROM NhanVien nv WHERE nv.email = :email")
    NhanVien findByEmail(@Param("email") String email);

    @Query(value = "SELECT nv FROM NhanVien nv WHERE nv.cccd = :cccd")
    NhanVien findByCccd(@Param("cccd") String cccd);

    @Modifying
    @Transactional
    @Query("UPDATE NhanVien nv SET nv.trangThai = :trangThai WHERE nv.id = :id")
    void updateTrangThaiById(@Param("trangThai") Integer trangThai, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE NhanVien nv SET nv.matKhau = :password WHERE nv.email = :email")
    void updatePasswordEmployee(String password, String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE NhanVien nv SET nv.hinhAnh = :image WHERE nv.id = :id")
    void updateImageEmployee(@Param("image") String image, @Param("id") Long id);

    @Query(value = "SELECT nv FROM NhanVien nv WHERE nv.id = :id")
    Optional<NhanVien> getNhanVienById(@Param("id") Long id);

    @Query(value = "SELECT nv FROM NhanVien  nv WHERE nv.id = :id")
    NhanVien findByIdNhanVien(@Param("id") Long id);

}

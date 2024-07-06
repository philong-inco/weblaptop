package com.dantn.weblaptop.nhanvien.Repository;

import com.dantn.weblaptop.constant.AccountStatus;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVien_Repository extends JpaRepository<NhanVien, Integer> {
//    @Query(value = "SELECT nv FROM NhanVien nv  WHERE " +
//            "(:search is null or nv.ma LIKE %:search% or nv.ten LIKE %:search% or nv.email like %:search% or nv.sdt like %:search%) " +
//            "and (:trangThai is null or nv.trangThai = :trangThai)")
//    Page<NhanVien> pageSearch(Pageable pageable, @Param("search") String search);
//
//    List<NhanVien> getNhanVienByTrangThai(AccountStatus trangThai);
//
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE NhanVien nv SET nv.trangThai = :trangThai WHERE nv.id = :id", nativeQuery = true)
//    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
//
//
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE NhanVien nv SET nv.matKhau = :newPassword WHERE nv.email = :email", nativeQuery = true)
//    void updatePassword(@Param("newPassword") String newPassword, @Param("email") String email);
//
//    NhanVien findBySdt(String sdt);
//
//    NhanVien findByEmail(String email);
//
//    NhanVien findBySoCanCuocCongDan(String soCanCuocCongDan);
//
//    Optional<NhanVien> findOneByEmailAndMatKhau(String email, String matKhau);
//
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE NhanVien nv SET nv.hinhAnh = :image WHERE nv.email = :email", nativeQuery = true)
//    void updateImageNV(@Param("image") String image, @Param("email") String email);
}

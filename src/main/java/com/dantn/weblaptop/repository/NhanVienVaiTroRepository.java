package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.NhanVienVaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienVaiTroRepository extends JpaRepository<NhanVienVaiTro, Integer> {
    @Query(value = "SELECT nvvt FROM NhanVienVaiTro nvvt WHERE nvvt.nhanVien = :nhanVienId")
    NhanVienVaiTro findNhanVienVaiTroByIdNhanVien(@Param("nhanVienId") Long nhanVienId);
}

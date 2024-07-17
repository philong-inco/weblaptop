package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.NhanVienVaiTro;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanVienVaiTroRepository extends JpaRepository<NhanVienVaiTro, Integer> {
    @Query(value = "SELECT nvvt FROM NhanVienVaiTro nvvt WHERE nvvt.nhanVien = :nhanVienId")
    NhanVienVaiTro findNhanVienVaiTroByIdNhanVien(@Param("nhanVienId") Long nhanVienId);

    @Query(value = "SELECT nvvt.vaiTro FROM NhanVienVaiTro nvvt WHERE nvvt.vaiTro.id = :idVaiTro")
    VaiTro findById(@Param("idVaiTro") Long idVaiTro);


    @Query(value = "SELECT vt FROM NhanVienVaiTro nvvt JOIN VaiTro vt ON nvvt.vaiTro.id = vt.id WHERE nvvt.nhanVien = :nhanVien")
    List<VaiTro> findByNhanVien(NhanVien nhanVien);

    @Query(value = "DELETE NhanVienVaiTro nvvt WHERE nvvt.nhanVien.id = :id")
    void deleteByNhanVien(@Param("id") Long id);
}

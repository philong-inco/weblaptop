package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
}

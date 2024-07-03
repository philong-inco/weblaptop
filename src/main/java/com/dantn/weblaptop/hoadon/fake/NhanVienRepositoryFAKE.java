package com.dantn.weblaptop.hoadon.fake;

import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepositoryFAKE extends JpaRepository<NhanVien, Long> {
}

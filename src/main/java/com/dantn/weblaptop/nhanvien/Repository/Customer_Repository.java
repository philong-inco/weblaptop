package com.dantn.weblaptop.nhanvien.Repository;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Customer_Repository extends JpaRepository<KhachHang, Long> {
}

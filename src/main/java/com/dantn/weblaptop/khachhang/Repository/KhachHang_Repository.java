package com.dantn.weblaptop.khachhang.Repository;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHang_Repository extends JpaRepository<KhachHang, Integer> {
}

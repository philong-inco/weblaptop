package com.dantn.weblaptop.hoadon.fake;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepositoryFAKE extends JpaRepository<KhachHang, Long> {
}

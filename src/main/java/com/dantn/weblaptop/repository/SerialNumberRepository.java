package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long> {
    List<SerialNumber> findBySanPhamChiTietIdAndTrangThai(Long productDetailId, Integer status);

    List<SerialNumber> findAllBySanPhamChiTietId(Long productDetailId);
}

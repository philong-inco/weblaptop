package com.dantn.weblaptop.repository;

import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, Long> {
    @Query(value = "SELECT id, ma, trang_thai AS trangThai, ten, mo_ta AS moTa, ngay_tao AS ngayTao, ngay_sua AS ngaySua, nguoi_tao AS nguoiTao, nguoi_sua AS nguoiSua FROM your_entity_table", nativeQuery = true)
    Page<HinhThucThanhToanResponse> getAll (Pageable pageable);
}

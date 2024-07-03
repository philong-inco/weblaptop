package com.dantn.weblaptop.hoadon.repository;

import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.hoadon.dto.response.HinhThucThanhToanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, Long> {
    @Query(value = "SELECT " +
            "id, " +
            "ngay_tao, " +
            "ngay_sua, " +
            "nguoi_tao, " +
            "nguoi_sua, " +
            "ma, " +
            "trang_thai, " +
            "ten, " +
            "mo_ta " +
            "FROM hinh_thuc_thanh_toan " +
            "ORDER BY nguoi_sua DESC",
            nativeQuery = true)
    Page<HinhThucThanhToanResponse> getAll(Pageable pageable);

}

package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.nguoidung.NguoiDung;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class HoaDonChiTiet extends BaseEntity {

    @Column(name = "")
    private BigDecimal giaSanPhamBanDau;

    @Column(name = "")
    private BigDecimal giaSanPhamSauKhuyenMai;

    @Column(name = "")
    private Integer soLuong;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sanphamchitiet_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "hoadon_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HoaDon hoaDon;

}

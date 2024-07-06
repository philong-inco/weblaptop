package com.dantn.weblaptop.entity.phieugiamgia;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "phieu_giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuGiamGia extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ten")
    String ten;
    @Column(name = "mo_ta")
    String moTa;
    @Column(name = "ngay_bat_dau")
    LocalDateTime ngayBatDau;
    @Column(name = "ngay_het_han")
    LocalDateTime ngayHetHan;
    @Column(name = "loai_giam_gia")
    Integer loaiGiamGia;
    @Column(name = "gia_tri_giam_gia")
    BigDecimal giaTriGiamGia;
    @Column(name = "gia_tri_don_toi_thieu")
    BigDecimal giaTriDonToiThieu;
    @Column(name = "giam_toi_da")
    BigDecimal giamToiGia;
    @Column(name = "pham_vi_ap_dung")
    Integer phamViApDung;
    @OneToMany(mappedBy = "phieuGiamGia",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    Set<HoaDon> hoaDon;
    @OneToMany(mappedBy = "phieuGiamGia",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    Set<KhachHangPhieuGiamGia> khachHangPhieuGiamGias;
}

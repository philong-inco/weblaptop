package com.dantn.weblaptop.entity.dotgiamgia;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "dot_giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DotGiamGia extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String ten;
    @Column(name = "mo_ta")
    String moTa;
    @Column(name = "loai_chiet_khau")
    Integer loaiChietKhau;
    @Column(name = "thoi_gian_bat_dau")
    LocalDateTime thoiGianBatDau;
    @Column(name = "thoi_gian_ket_thuc")
    LocalDateTime thoiGianKetthuc;
    @Column(name = "giam_toi_da")
    BigDecimal giamToiDa;
    @Column(name = "gia_tri_giam")
    Integer giaTriGiam;
    @OneToMany(mappedBy = "dotGiamGia", fetch = FetchType.LAZY)
    Set<DotGiamGiaSanPhamChiTiet> dotGiamGiaSanPhamChiTiets;
    @OneToMany(mappedBy = "dotGiamGia", fetch = FetchType.LAZY)
    Set<SerialNumberDaBan> serialNumberDaBans;
}


package com.dantn.weblaptop.entity.khachhang;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "dia_chi")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChi extends BaseEntity {
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "loai_dia_chi")
    Integer loaiDiaChi;
    @Column(name = "ten_nguoi_nhan")
    String tenNguoiNhan;
    @Column(name = "sdt_nguoi_nhan")
    String sdtNguoiNhan;
    @Column(name = "email_nguoi_nhan")
    String emailNguoiNhan;
    @Column(name = "dia_chi_nhan_hang")
    String diaChiNhanHang;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "khach_hang_id")
    @ToString.Exclude
    private KhachHang khachHang;
}

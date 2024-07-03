package com.dantn.weblaptop.entity.danhgia;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
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

@Entity
@Table(name = "danh_gia")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhGia extends BaseEntity {

    @Column(name = "so_diem")
    Integer soDiem;
    @Column(name = "noi_dung")
    String noiDung;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "khach_hang_id")
    KhachHang khachHang;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_chi_tiet_id")
    SanPhamChiTiet sanPhamChiTiet;
}

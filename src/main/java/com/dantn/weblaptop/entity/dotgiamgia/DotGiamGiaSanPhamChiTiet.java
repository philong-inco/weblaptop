package com.dantn.weblaptop.entity.dotgiamgia;

import com.dantn.weblaptop.entity.base.BaseEntity;
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
@Table(name = "dot_giam_gia_san_pham_chi_tiet")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DotGiamGiaSanPhamChiTiet extends BaseEntity {

    @Column(name = "trang_thai")
    Integer trangThai;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_chi_tiet_id")
    SanPhamChiTiet sanPhamChiTiet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dot_giam_gia_id")
    DotGiamGia dotGiamGia;
}

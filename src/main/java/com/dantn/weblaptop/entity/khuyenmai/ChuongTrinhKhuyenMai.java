package com.dantn.weblaptop.entity.khuyenmai;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "chuong_trinh_khuyen_mai")
@Data
public class ChuongTrinhKhuyenMai extends BaseEntity {

    @Column(name = "ma_khuyen_mai")
    private String maKhuyenMai;

    @Column(name = "ten_khuyen_mai")
    private String tenKhuyenMai;

    @Column(name = "mo_ta_khuyen_mai")
    private String moTaKhuyenMai;

    @Column(name = "ngay_bat_dau")
    private Long ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Long ngayKetThuc;

    @Column(name = "gia_tri")
    private Float giaTri;

    @Column(name = "giam_toi_da")
    private Float giamToiDa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "quanhuyen_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LoaiKhuyenMai loaiKhuyenMai;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(name = "khuyemmai_hoadon",
            joinColumns = @JoinColumn(name = "khuyenmai_id"),
            inverseJoinColumns = @JoinColumn(name = "hoadon_id"))
    private Set<HoaDon> hoaDons;
}

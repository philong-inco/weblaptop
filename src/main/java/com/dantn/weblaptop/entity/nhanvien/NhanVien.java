package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.CoSo;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.nguoidung.QuanHuyen;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "nhan_vien")
public class NhanVien extends BaseEntity {

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "ngay_sinh")
    private Long ngaySinh;

    @Column(name = "ngay_bat_dau")
    private Long ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Long ngayKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "phanquyen_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PhanQuyen phanQuyen;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "coso_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CoSo coSo;

    @OneToMany(mappedBy = "nhanVien",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDon> hoaDons;

    public void addHoaDon(HoaDon hoaDon) {
        if (hoaDons == null) {
            hoaDons = new HashSet<>();
        }
        hoaDons.add(hoaDon);
    }
}

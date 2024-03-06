package com.dantn.weblaptop.entity.nguoidung;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.voucher.Voucher;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "nguoi_dung")
@Data
@EqualsAndHashCode(callSuper = false)
public class NguoiDung extends BaseEntity {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "nguoiDung",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ThongTinNguoiDung> thongTinNguoiDungs;

    @OneToMany(mappedBy = "nguoiDung",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDon> hoaDons;

    @OneToMany(mappedBy = "nguoiDung",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<GioHangChiTiet> gioHangChiTiets;

    @ManyToMany(mappedBy = "nguoiDungs",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<SanPham> sanPhams;

    @ManyToMany(mappedBy = "nguoiDungs", cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<Voucher> vouchers;


    public void addGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        if (gioHangChiTiets == null) {
            gioHangChiTiets = new HashSet<>();
        }
        gioHangChiTiets.add(gioHangChiTiet);
    }

    public void addHoaDon(HoaDon hoaDon) {
        if (hoaDons == null) {
            hoaDons = new HashSet<>();
        }
        hoaDons.add(hoaDon);
    }

    public void addThongTinNguoiDung(ThongTinNguoiDung thongTinNguoiDung) {
        if (thongTinNguoiDungs == null) {
            thongTinNguoiDungs = new HashSet<>();
        }
        thongTinNguoiDungs.add(thongTinNguoiDung);
    }

}

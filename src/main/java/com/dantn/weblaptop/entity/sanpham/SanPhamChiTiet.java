package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.TonKho;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.hoadon.HoaDonChiTiet;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "san_pham_chi_tiet")
@Data
@EqualsAndHashCode(callSuper = false)
public class SanPhamChiTiet extends BaseEntity {

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_san_pham")
    private BigDecimal giaSanPham;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SanPham sanPham;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ram")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RAM ram;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cpu")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CPU cpu;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vga")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private VGACard vga;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_man_hinh")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ManHinh manHinh;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loai_bao_hanh")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LoaiBaoHanh loaiBaoHanh;

    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDonChiTiet> hoaDonChiTiets;

    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<GioHangChiTiet> gioHangChiTiets;

    /* chua can thiet lam :D
    @OneToMany( mappedBy = "coSo",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<TonKho> tonKhos;

    public void addTonKho(TonKho tonKho){
        if (tonKhos == null){
            tonKhos = new HashSet<>();
        }
        tonKhos.add(tonKho);
    }
    **/


    public void addGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        if (gioHangChiTiets == null) {
            gioHangChiTiets = new HashSet<>();
        }
        gioHangChiTiets.add(gioHangChiTiet);
    }

    public void addHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        if (hoaDonChiTiets == null) {
            hoaDonChiTiets = new HashSet<>();
        }
        hoaDonChiTiets.add(hoaDonChiTiet);
    }


}

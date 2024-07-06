package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.danhgia.DanhGia;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;


/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "san_pham_chi_tiet")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamChiTiet extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "gia_ban")
    BigDecimal giaBan;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "ram_id")
    @ToString.Exclude
    RAM ram;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "cpu_id")
    @ToString.Exclude
    CPU cpu;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "vga_id")
    @ToString.Exclude
    VGA vga;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "man_hinh_id")
    @ToString.Exclude
    ManHinh manHinh;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "o_cung_id")
    @ToString.Exclude
    OCung oCung;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "mau_sac_id")
    @ToString.Exclude
    MauSac mauSac;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "ban_phim_id")
    @ToString.Exclude
    BanPhim banPhim;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "he_dieu_hanh_id")
    @ToString.Exclude
    HeDieuHanh heDieuHanh;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "webcam")
    @ToString.Exclude
    Webcam webcam;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_id")
    @ToString.Exclude
    SanPham sanPham;
    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<AnhSanPham> anhSanPhams;
    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<DanhGia> danhGias;
    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<SerialNumber> serialNumbers;
    @OneToMany(mappedBy = "sanPhamChiTiet",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<DotGiamGiaSanPhamChiTiet> dotGiamGiaSanPhamChiTiets;
}

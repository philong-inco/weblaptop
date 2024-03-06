package com.dantn.weblaptop.entity.coso;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.nguoidung.PhuongXa;
import com.dantn.weblaptop.entity.nguoidung.QuanHuyen;
import com.dantn.weblaptop.entity.nguoidung.ThanhPho;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
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
@Table(name = "co_so_kinh_doanh")
@Data
public class CoSo extends BaseEntity {

    @Column(name = "ma_co_so")
    private String maCoSo;

    @Column(name = "ten_co_so")
    private String tenCoSo;

    @Column(name = "mo_ta_co_so")
    private String moTaCoSo;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "thanhpho_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ThanhPho thanhPho;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "quanhuyen_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private QuanHuyen quanHuyen;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "phuongxa_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PhuongXa phuongXa;

    @OneToMany(mappedBy = "coSo",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<NhanVien> nhanViens;

    @OneToMany(mappedBy = "coSo",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDon> hoaDons;

    @OneToMany(mappedBy = "coSo",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<TonKho> tonKhos;

    public void addTonKho(TonKho tonKho) {
        if (tonKhos == null) {
            tonKhos = new HashSet<>();
        }
        tonKhos.add(tonKho);
    }

    public void addHoaDon(HoaDon hoaDon) {
        if (hoaDons == null) {
            hoaDons = new HashSet<>();
        }
        hoaDons.add(hoaDon);
    }

    public void addNhanVien(NhanVien nhanVien) {
        if (nhanViens == null) {
            nhanViens = new HashSet<>();
        }
        nhanViens.add(nhanVien);
    }

}

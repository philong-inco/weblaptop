package com.dantn.weblaptop.entity.nguoidung;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.CoSo;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
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
@Table(name = "thanh_pho")
@Data
@EqualsAndHashCode(callSuper = false)
public class ThanhPho extends BaseEntity {

    @Column(name = "ten_thanh_pho")
    private String tenThanhPho;

    @Column(name = "trang_thai")
    private String trangThai;

    @OneToMany(mappedBy = "thanhPho",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<QuanHuyen> quanHuyens;

    @OneToMany(mappedBy = "thanhPho",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ThongTinNguoiDung> thongTinNguoiDungs;

    @OneToMany(mappedBy = "thanhPho",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CoSo> coSos;

    public void addCoSo(CoSo coSo) {
        if (coSos == null) {
            coSos = new HashSet<>();
        }
        coSos.add(coSo);
    }

    public void addThongTinNguoiDung(ThongTinNguoiDung thongTinNguoiDung) {
        if (thongTinNguoiDungs == null) {
            thongTinNguoiDungs = new HashSet<>();
        }
        thongTinNguoiDungs.add(thongTinNguoiDung);
    }

    public void addQuanHuyen(QuanHuyen quanHuyen) {
        if (quanHuyens == null) {
            quanHuyens = new HashSet<>();
        }
        quanHuyens.add(quanHuyen);
    }

}

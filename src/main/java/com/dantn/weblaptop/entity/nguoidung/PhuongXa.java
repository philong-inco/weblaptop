package com.dantn.weblaptop.entity.nguoidung;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.CoSo;
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
@Table(name = "phuong_xa")
@Data
@EqualsAndHashCode(callSuper = false)
public class PhuongXa extends BaseEntity {

    @Column(name = "ten_phuong_xa")
    private String tenPhuongXa;

    @Column(name = "trang_thai")
    private String trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "quanhuyen_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private QuanHuyen quanHuyen;

    @OneToMany(mappedBy = "phuongXa",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ThongTinNguoiDung> thongTinNguoiDungs;

    @OneToMany(mappedBy = "phuongXa",
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


}

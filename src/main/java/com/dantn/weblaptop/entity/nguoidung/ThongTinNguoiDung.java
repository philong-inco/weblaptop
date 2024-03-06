package com.dantn.weblaptop.entity.nguoidung;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "thong_tin_nguoi_dung")
@Data
@EqualsAndHashCode(callSuper = false)
public class ThongTinNguoiDung extends BaseEntity {

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoidung_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "thongTinNguoiDung",
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

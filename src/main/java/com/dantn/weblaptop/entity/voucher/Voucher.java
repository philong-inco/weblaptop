package com.dantn.weblaptop.entity.voucher;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.nguoidung.NguoiDung;
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
@Table(name = "voucher")
@Data
public class Voucher extends BaseEntity {

    @Column(name = "ma_voucher")
    private String maVoucher;

    @Column(name = "ten_voucher")
    private String tenVoucher;

    @Column(name = "mo_ta_voucher")
    private String moTaVoucher;

    @Column(name = "so_lan_su_dung_toi_da")
    private Integer soLanSuDungToiDa;

    @Column(name = "dieu_kien_ap_dung")
    private Float dieuKienApDung;

    @Column(name = "ngay_hieu_luc")
    private Long ngayHieuLuc;

    @Column(name = "ngay_ket_thuc")
    private Long ngayKetThuc;

    @Column(name = "trangThai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "loaivoucher_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LoaiVoucher loaiVoucher;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "voucher_hoadon",
            joinColumns = @JoinColumn(name = "voucher_id"),
            inverseJoinColumns = @JoinColumn(name = "hoadon_id"))
    private Set<HoaDon> hoaDons;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "voucher_nguoidung",
            joinColumns = @JoinColumn(name = "voucher_id"),
            inverseJoinColumns = @JoinColumn(name = "nguoidung_id"))
    private Set<NguoiDung> nguoiDungs;

}

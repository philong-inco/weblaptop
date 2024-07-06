package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "hoa_don")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDon extends BaseEntity {

    @Column(name = "ma")
    String ma;
    @Column(name = "trang_thai")
    @Enumerated(EnumType.ORDINAL)
    HoaDonStatus trangThai;
    @Column(name = "loai_hoa_don")
    Integer loaiHoaDon;
    @Column(name = "tong_tien_ban_dau")
    BigDecimal tongTienBanDau;
    @Column(name = "tong_tien_phai_tra")
    BigDecimal tongTienPhaiTra;
    @Column(name = "ngay_nhan_hang_mong_muon")
    LocalDateTime ngayNhanHangMongMuon;
    @Column(name = "sdt")
    String sdt;
    @Column(name = "email")
    String email;
    @Column(name = "dia_chi")
    String diaChi;
    @Column(name = "ghi_chu")
    String ghiChu;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "nhan_vien_id")
    @ToString.Exclude
    NhanVien nhanVien;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "phieu_giam_gia_id")
    @ToString.Exclude
    PhieuGiamGia phieuGiamGia;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "khach_hang_id")
    @ToString.Exclude
    KhachHang khachHang;
    @OneToMany(mappedBy = "hoaDon",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    Set<SerialNumberDaBan> serialNumberDaBans;
    @OneToMany(mappedBy = "hoaDon",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    Set<HoaDonHinhThucThanhToan> hoaDonHinhThucThanhToans;
    @OneToMany(mappedBy = "hoaDon",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    Set<LichSuHoaDon> lichSuHoaDons;
}

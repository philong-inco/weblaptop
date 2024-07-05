package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "nhan_vien")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVien extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String cccd;
    @Column(name = "ho")
    String ten;
    @Column(name = "email")
    String email;
    @Column(name = "mat_khau")
    String matKhau;
    @Column(name = "sdt")
    String sdt;
    @Column(name = "ngay_sinh")
    LocalDateTime ngaySinh;
    @Column(name = "gioi_tinh")
    Integer gioiTinh;
    @Column(name = "hinh_anh")
    String hinhAnh;
    @Column(name = "tai_khoan_ngan_hang")
    String taiKhoanNganHang;
    @Column(name = "ngay_bat_dau_lam_viet")
    LocalDateTime ngayBatDauLamViec;
    @Column(name = "ngay_thoi_viet")
    LocalDateTime ngayThoiViec;
    @OneToMany(mappedBy = "nhanVien",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<NhanVienVaiTro> nhanVienVaiTros;
    @OneToMany(mappedBy = "nhanVien",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<LichSuHoaDon> lichSuHoaDons;
    @OneToMany(mappedBy = "nhanVien",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<HoaDon> hoaDons;

}

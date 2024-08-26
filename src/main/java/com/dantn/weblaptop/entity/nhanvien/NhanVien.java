package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "nhan_vien")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVien extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ten")
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
    @Column(name = "dia_chi")
    String diaChi;
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

    @OneToMany(mappedBy = "nhanVien",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<LichLamViec> lichLamViecs;
}

package com.dantn.weblaptop.entity.khachhang;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.danhgia.DanhGia;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;


/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "khach_hang")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class KhachHang extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String ho;
    String ten;
    String email;
    String sdt;
    @Column(name = "ngay_sinh")
    LocalDateTime ngaySinh;
    @Column(name = "gioi_tinh")
    Integer gioiTinh;
    @Column(name = "hinh_anh")
    String hinhAnh;
    @Column(name = "hang_khach_hang")
    Integer hangKhachHang;
    @Column(name = "session_id")
    String sessionId;
    @OneToMany(mappedBy = "khachHang",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<DiaChi> diaChis;
    @OneToMany(mappedBy = "khachHang",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<ThongBaoKhachHang> thongBaoKhachHangs;
    @OneToMany(mappedBy = "khachHang",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<DanhGia> danhGias;
    @OneToMany(mappedBy = "khachHang",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<KhachHangPhieuGiamGia> khachHangPhieuGiamGias;
    @OneToMany(mappedBy = "khachHang",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<LichSuHoaDon> lichSuHoaDons;
}

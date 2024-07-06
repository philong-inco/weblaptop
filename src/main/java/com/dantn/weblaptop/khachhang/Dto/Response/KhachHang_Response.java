//package com.dantn.weblaptop.khachhang.Dto.Response;
//
//import com.dantn.weblaptop.entity.danhgia.DanhGia;
//import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
//import com.dantn.weblaptop.entity.khachhang.DiaChi;
//import com.dantn.weblaptop.entity.khachhang.ThongBaoKhachHang;
//import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.OneToMany;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//
//@Getter
//@Setter
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@AllArgsConstructor
//@NoArgsConstructor
//public class KhachHang_Response {
//    String ma;
//    @Column(name = "trang_thai")
//    Integer trangThai;
//
//    String ho;
//
//    String ten;
//
//    String email;
//
//    String sdt;
//
//    LocalDateTime ngaySinh;
//
//    Integer gioiTinh;
//
//    String hinhAnh;
//
//    String sessionId;
//
//    Set<DiaChi> diaChis;
//
//    Set<ThongBaoKhachHang> thongBaoKhachHangs;
//
//    Set<DanhGia> danhGias;
//
//    Set<KhachHangPhieuGiamGia> khachHangPhieuGiamGias;
//
//    Set<LichSuHoaDon> lichSuHoaDons;
//}

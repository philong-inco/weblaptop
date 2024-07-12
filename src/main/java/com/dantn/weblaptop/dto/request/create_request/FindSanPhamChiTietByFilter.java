package com.dantn.weblaptop.dto.request.create_request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FindSanPhamChiTietByFilter {
    String tenSanPham;
    String maSanPham;
    String maSanPhamChiTiet;
    String trangThai;
    String ngayTaoSau;
    String ngayTaoTruoc;
    String ngaySuaSau;
    String ngaySuaTruoc;
    String thuongHieu;
    String nhuCau;
    String mauSac;
    String ram;
    String cpu;
    String vga;
    String webcam;
    String oCung;
    String manHinh;
    String heDieuHanh;
    String banPhim;
    String giaNhoHon;
    String giaLonHon;
}

package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHangDetailResponse {
    Long idGioHang;
    Long idGioHangChiTiet;
    Long idSanPham;
    Long idSanPhamSPCT;
    String anh;
    String maSPCT;
    String tenSanPham;
    String moTaSP;
    String nhuCau;
    String thuongHieu;
    String banPhim;
    String cpu;
    String heDieuHanh;
    String manHinh;
    String mauSac;
    String oCung;
    String ram;

    String vga;
    String webcam;
//    SanPhamChiTietClientDTO sanPhamChiTietClientDTO;

    BigDecimal gia;
    Integer soLuong;
    Integer trangThai;

}

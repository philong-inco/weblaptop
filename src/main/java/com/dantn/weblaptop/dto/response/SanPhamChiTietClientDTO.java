package com.dantn.weblaptop.dto.response;

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
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamChiTietClientDTO {
    Long idSPCT;
    String sanPhamId;
    String maSP;
    String tenSP;
    String moTaSP;
    String nhuCau;
    String thuongHieu;
    String giaBan;
    String banPhim;
    String cpu;
    String heDieuHanh;
    String manHinh;
    String mauSac;
    String oCung;
    String ram;
    String giaSauKhuyenMai;
    String soTienDuocGiam;
    String tonKhoConLai;
    String vga;
    String webcam;
    String tenKhuyenMai;
    String listUrlAnhSanPham;
}

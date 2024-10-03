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
    Long id;
    String maSPCT;
    Long sanPhamId;
    String maSP;
    String tenSanPham;
    String moTaSP;
    Long idNhuCau;
    String nhuCau;
    Long idThuongHieu;
    String thuongHieu;
    String giaBan;
    Long idBanPhim;
    String banPhim;
    Long idCPU;
    String cpu;
    Long idHeDieuHanh;
    String heDieuHanh;
    Long idManHinh;
    String manHinh;
    Long idMauSac;
    String mauSac;
    Long idOcung;
    String oCung;
    Long idRam;
    String ram;
    String giaSauKhuyenMai;
    String soTienDuocGiam;
    String tonKhoConLai;
    Long idVGA;
    String vga;
    Long idWebcam;
    String webcam;
    Long idKhuyenMai;
    String maKhuyenMai;
    String tenKhuyenMai;
    String listUrlAnhSanPham;
}

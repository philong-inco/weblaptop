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
public class FindSanPhamFilterByName {
    String tenSanPham;
    String ma;
    String trangThai;
    String ngayTaoSau;
    String ngayTaoTruoc;
    String ngaySuaSau;
    String ngaySuaTruoc;
    String[] tenThuongHieu;
    String[] tenNhuCau;
    String[] tenMau;
    String[] tenRam;
    String[] tenCPU;
    String[] tenVGA;
    String[] tenWebcam;
    String[] tenOCung;
    String[] tenManHinh;
    String[] tenHeDieuHanh;
    String[] tenBanPhim;
    String giaNhoHon;
    String giaLonHon;
    String coDotGiamGia;
}

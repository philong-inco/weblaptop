package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.util.ConvertTime;
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
public class SanPhamChiTietResponse {
    Long id;
    String ma;
    String giaBan;
    Integer trangThai;
    String banPhim;
    String cpu;
    String heDieuHanh;
    String manHinh;
    String mauSac;
    String oCung;
    String ram;
    String sanPham;
    String vga;
    String webcam;
    String ngayTao;
    String nguoiTao;
    String ngaySua;
    String nguoiSua;
    String listSerialNumber;
    String listUrlAnhSanPham;

    public void convertTime(){
        this.ngayTao = ConvertTime.convert(this.ngayTao);
        this.ngaySua = ConvertTime.convert(this.ngaySua);
    }
}

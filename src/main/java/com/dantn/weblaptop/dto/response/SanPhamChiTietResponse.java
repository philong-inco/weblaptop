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
    Long idBanPhim;
    String cpu;
    Long idCPU;
    String heDieuHanh;
    Long idHeDieuHanh;
    String manHinh;
    Long idManHinh;
    String mauSac;
    Long idMauSac;
    String oCung;
    Long idOCung;
    String ram;
    Long idRam;
    String sanPham;
    String maSanPham;
    String sanPhamId;
    String vga;
    Long idVGA;
    String webcam;
    Long idWebcam;
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

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;

public class HoaDonMapper {

    public static HoaDonResponse toHoaDonResponse (HoaDon hoaDon){
        HoaDonResponse response = new HoaDonResponse();
        response.setId(hoaDon.getId());
        response.setMa(hoaDon.getMa());
        response.setIdNhanVien(hoaDon.getNhanVien().getId());
        response.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
//        response.setNgayMongMuonNhanHang(ConvertTime.convert(hoaDon.getNguoiTao()));
        //

        response.setNguoiSua(hoaDon.getNguoiSua());
        response.setNguoiTao(hoaDon.getNguoiTao());
        response.setTrangThai(hoaDon.getTrangThai());
        return response;
    }


    public  static  void toUpdateHoaDon (HoaDon hoaDon){

    }
}

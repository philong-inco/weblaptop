package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.ConvertTime;

import java.math.BigDecimal;

public class HoaDonMapper {

    public static HoaDonResponse  toHoaDonResponse (HoaDon hoaDon){
        HoaDonResponse response = new HoaDonResponse();
        KhachHang customer = hoaDon.getKhachHang();
        response.setId(hoaDon.getId());
        response.setMa(hoaDon.getMa());
        response.setIdNhanVien(hoaDon.getNhanVien().getId());
        response.setTenKhachHang(
                customer !=null ? customer.getHo()+" " + customer.getTen() : null);
        response.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
        response.setTongTienPhaiTra(hoaDon.getTongTienPhaiTra());
        response.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
//        response.setNgayMongMuonNhanHang(ConvertTime.convert(hoaDon.getNguoiTao()));
        response.setTongTienPhaiTra(hoaDon.getTongTienPhaiTra());
        response.setTongTienBanDau(hoaDon.getTongTienBanDau());
        response.setTongSanPham(1000);
        // xem lại
        response.setSdt(hoaDon.getSdt());
        response.setEmail(hoaDon.getEmail() );
        response.setNguoiSua(hoaDon.getNguoiSua());
        response.setNguoiTao(hoaDon.getNguoiTao());
        response.setNgayTao(ConvertTime.convert(hoaDon.getNgayTao()+""));
        response.setTrangThai(hoaDon.getTrangThai());
        PhieuGiamGia phieuGiamGia = hoaDon.getPhieuGiamGia();
        if(phieuGiamGia!=null){
            response.setGiaTriPhieuGiamGia(phieuGiamGia.getGiaTriGiamGia());
            response.setLoaiPGG(phieuGiamGia.getLoaiGiamGia());
            response.setMaPGG(phieuGiamGia.getMa());
        }
        return response;
    }


    public  static  void toUpdateHoaDon (HoaDon hoaDon){

    }
}

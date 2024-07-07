package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;

public class LichSuHoaDonMapper {
    public static LichSuHoaDonResponse toBillHistoryResponse(LichSuHoaDon lichSuHoaDon) {
        LichSuHoaDonResponse response = new LichSuHoaDonResponse();
        response.setId(lichSuHoaDon.getId());

        response.setHoaDon(lichSuHoaDon.getHoaDon().getId());
        response.setMaHoaDon(lichSuHoaDon.getHoaDon().getMa());

        response.setTrangThai(lichSuHoaDon.getTrangThai());
        response.setGhiChuChoCuaHang(lichSuHoaDon.getGhiChuChoCuaHang());
        response.setGhiChuChoKhachHang(lichSuHoaDon.getGhiChuChoKhachHang());
        if (lichSuHoaDon.getKhachHang() != null) {
            response.setIdKhachHanh(lichSuHoaDon.getKhachHang().getId());
            response.setMaKhachHang(lichSuHoaDon.getKhachHang().getMa());
        }
        response.setIdNhanVien(lichSuHoaDon.getNhanVien().getId());
        response.setMaNhanVien(lichSuHoaDon.getNhanVien().getMa());

        response.setNgaySua(lichSuHoaDon.getNgaySua());
        response.setNgayTao(lichSuHoaDon.getNgayTao());
        response.setNguoiSua(lichSuHoaDon.getNguoiSua());
        response.setNguoiTao(lichSuHoaDon.getNguoiTao());

        return response;
    }

    public static LichSuHoaDon createBillHistory(CreateLichSuHoaDonRequest request) {
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setTrangThai(request.getTrangThai());
        lichSuHoaDon.setGhiChuChoCuaHang(request.getGhiChuChoCuaHang());
        lichSuHoaDon.setGhiChuChoKhachHang(request.getGhiChuChoKhachHang());
        return lichSuHoaDon;
    }
}

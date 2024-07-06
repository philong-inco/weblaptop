package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.dto.LichSuHoaDonRquest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;

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
        return response;
    }

    public static LichSuHoaDon createBillHistory(LichSuHoaDonRquest request) {
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setTrangThai(request.getTrangThai());
        lichSuHoaDon.setGhiChuChoCuaHang(request.getGhiChuChoCuaHang());
        lichSuHoaDon.setGhiChuChoKhachHang(request.getGhiChuChoKhachHang());
        return lichSuHoaDon;
    }
}

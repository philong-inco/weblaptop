package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.KhachHangPhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.ConvertTime;

public class PhieuGiamGiaMapper {

    public static PhieuGiamGiaResponse toPhieuGiamGiaResponse(PhieuGiamGia phieuGiamGia) {
        PhieuGiamGiaResponse response = new PhieuGiamGiaResponse();
        response.setId(phieuGiamGia.getId());
        response.setMa(phieuGiamGia.getMa());
        response.setTrangThai(phieuGiamGia.getTrangThai());
        response.setTen(phieuGiamGia.getTen());
        response.setMoTa(phieuGiamGia.getMoTa());
        response.setNgayBatDau(phieuGiamGia.getNgayBatDau());
        response.setNgayHetHan(phieuGiamGia.getNgayHetHan());
        response.setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
        response.setGiaTriGiamGia(phieuGiamGia.getGiaTriGiamGia());
        response.setGiaTriDonToiThieu(phieuGiamGia.getGiaTriDonToiThieu());
        response.setGiamToiGia(phieuGiamGia.getGiamToiGia());
        response.setPhamViApDung(phieuGiamGia.getPhamViApDung());
        response.setNgayTao(ConvertTime.convert(phieuGiamGia.getNgayTao().toString()));
        response.setNgaySua(ConvertTime.convert(phieuGiamGia.getNgaySua().toString()));
        response.setNguoiTao(phieuGiamGia.getNguoiTao());
        response.setNguoiSua(phieuGiamGia.getNguoiSua());
        response.setKhachHangPhieuGiamGias(phieuGiamGia.getKhachHangPhieuGiamGias());
        return response;
    }

    public static PhieuGiamGia toCreatePGG(CreatePhieuGiamGiaRequest request) {
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        phieuGiamGia.setTrangThai(0);
        phieuGiamGia.setTen(request.getTen());
        phieuGiamGia.setMoTa(request.getMoTa());
        phieuGiamGia.setNgayBatDau(request.getNgayBatDau());
        phieuGiamGia.setNgayHetHan(request.getNgayHetHan());
        phieuGiamGia.setLoaiGiamGia(request.getLoaiGiamGia());
        phieuGiamGia.setGiaTriGiamGia(request.getGiaTriGiamGia());
        phieuGiamGia.setGiaTriDonToiThieu(request.getGiaTriDonToiThieu());
        phieuGiamGia.setGiamToiGia(request.getGiamToiGia());
        phieuGiamGia.setPhamViApDung(request.getPhamViApDung());
        phieuGiamGia.setSoLuong(request.getSoLuong());
        phieuGiamGia.setNgayTao(request.getNgayTao());
        phieuGiamGia.setNgaySua(request.getNgaySua());
        phieuGiamGia.setNguoiTao(request.getNguoiTao());
        phieuGiamGia.setNguoiSua(request.getNguoiSua());
        return phieuGiamGia;
    }

    public static KhachHangPhieuGiamGiaResponse toKhachHangPhieuGiamGiaResponse(KhachHangPhieuGiamGia khachHangPhieuGiamGia) {
        KhachHangPhieuGiamGiaResponse response = new KhachHangPhieuGiamGiaResponse();
        response.setMaPhieu(khachHangPhieuGiamGia.getPhieuGiamGia().getMa());
        response.setEmail(khachHangPhieuGiamGia.getKhachHang().getEmail());
        response.setId(khachHangPhieuGiamGia.getId());
        response.setIdKhachHang(khachHangPhieuGiamGia.getKhachHang().getId());
        response.setTrangThai(khachHangPhieuGiamGia.getTrangThai());
        return response;
    }
}

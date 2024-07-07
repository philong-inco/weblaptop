package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.ConvertTime;

public class PhieuGiamGiaMapper {

    public static PhieuGiamGiaResponse toPhieuGiamGiaResponse (PhieuGiamGia phieuGiamGia){
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
        return  response;
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.KhachHangPhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.ConvertTime;

import java.util.Set;
import java.util.stream.Collectors;

public class PhieuGiamGiaMapper {

    public static PhieuGiamGiaResponse toPhieuGiamGiaResponse(PhieuGiamGia phieuGiamGia) {
        PhieuGiamGiaResponse response = new PhieuGiamGiaResponse();
        response.setId(phieuGiamGia.getId());
        response.setMa(phieuGiamGia.getMa());
        response.setTrangThai(phieuGiamGia.getTrangThai());
        response.setTen(phieuGiamGia.getTen());
        response.setSoLuong(phieuGiamGia.getSoLuong());
        response.setMoTa(phieuGiamGia.getMoTa());
        response.setNgayBatDau(phieuGiamGia.getNgayBatDau());
        response.setNgayHetHan(phieuGiamGia.getNgayHetHan());
        response.setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
        response.setGiaTriGiamGia(phieuGiamGia.getGiaTriGiamGia());
        response.setGiaTriDonToiThieu(phieuGiamGia.getGiaTriDonToiThieu());
        response.setGiamToiGia(phieuGiamGia.getGiamToiDa());
        response.setPhamViApDung(phieuGiamGia.getPhamViApDung());
        response.setNgayTao(ConvertTime.convert(phieuGiamGia.getNgayTao().toString()));
        response.setNgaySua(ConvertTime.convert(phieuGiamGia.getNgaySua().toString()));
        response.setNguoiTao(phieuGiamGia.getNguoiTao());
        response.setNguoiSua(phieuGiamGia.getNguoiSua());
        if (phieuGiamGia.getKhachHangPhieuGiamGias() != null) {
            Set<Long> khachHangIds = phieuGiamGia.getKhachHangPhieuGiamGias().stream()
                    .map(khachHangPhieuGiamGia -> khachHangPhieuGiamGia.getKhachHang().getId())
                    .collect(Collectors.toSet());
            response.setKhachHangPhieuGiamGias(khachHangIds);
        }

        return response;
    }

    public static PhieuGiamGia toCreatePGG(CreatePhieuGiamGiaRequest request) {
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
//        phieuGiamGia.setTrangThai(0);
        phieuGiamGia.setTen(request.getTen());
        phieuGiamGia.setMoTa(request.getMoTa());
        phieuGiamGia.setNgayBatDau(request.getNgayBatDau());
        phieuGiamGia.setNgayHetHan(request.getNgayHetHan());
        phieuGiamGia.setLoaiGiamGia(request.getLoaiGiamGia());
        phieuGiamGia.setGiaTriGiamGia(request.getGiaTriGiamGia());
        phieuGiamGia.setGiaTriDonToiThieu(request.getGiaTriDonToiThieu());
        phieuGiamGia.setGiamToiDa(request.getGiamToiDa());
        phieuGiamGia.setPhamViApDung(request.getPhamViApDung());
        phieuGiamGia.setSoLuong(request.getSoLuong());
        phieuGiamGia.setNgayTao(request.getNgayTao());
        phieuGiamGia.setNgaySua(request.getNgaySua());
        phieuGiamGia.setNguoiTao(request.getNguoiTao());
        phieuGiamGia.setNguoiSua(request.getNguoiSua());
        return phieuGiamGia;
    }

    public static void toUpdatePGG(UpdatePhieuGiamGiaRequest request, PhieuGiamGia phieuGiamGia) {
        // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy

        phieuGiamGia.setTen(request.getTen());
        phieuGiamGia.setMoTa(request.getMoTa());
        phieuGiamGia.setNgayBatDau(request.getNgayBatDau());
        phieuGiamGia.setNgayHetHan(request.getNgayHetHan());
        phieuGiamGia.setLoaiGiamGia(request.getLoaiGiamGia());
        phieuGiamGia.setGiaTriGiamGia(request.getGiaTriGiamGia());
        phieuGiamGia.setGiaTriDonToiThieu(request.getGiaTriDonToiThieu());
        phieuGiamGia.setGiamToiDa(request.getGiamToiGia());
        phieuGiamGia.setPhamViApDung(request.getPhamViApDung());
        phieuGiamGia.setSoLuong(request.getSoLuong());
//        phieuGiamGia.setNgayTao(request.getNgayTao());
//        phieuGiamGia.setNgaySua(request.getNgaySua());
//        phieuGiamGia.setNguoiTao(request.getNguoiTao());
//        phieuGiamGia.setNguoiSua(request.getNguoiSua());
    }

    public static KhachHangPhieuGiamGiaResponse toKhachHangPhieuGiamGiaResponse(KhachHangPhieuGiamGia khachHangPhieuGiamGia) {
        KhachHangPhieuGiamGiaResponse response = new KhachHangPhieuGiamGiaResponse();
        response.setMaPhieu(khachHangPhieuGiamGia.getPhieuGiamGia().getMa());
        response.setEmail(khachHangPhieuGiamGia.getKhachHang().getEmail());
        response.setId(khachHangPhieuGiamGia.getId());
        response.setIdKhachHang(khachHangPhieuGiamGia.getKhachHang().getId());
        response.setTrangThai(khachHangPhieuGiamGia.getTrangThai());
        response.setSdt(khachHangPhieuGiamGia.getKhachHang().getSdt());
        response.setMaKhachHang(khachHangPhieuGiamGia.getKhachHang().getMa());
        return response;
    }
}

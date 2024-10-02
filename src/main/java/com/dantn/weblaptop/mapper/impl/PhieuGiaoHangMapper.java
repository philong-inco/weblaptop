package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiaoHang;
import com.dantn.weblaptop.dto.response.PhieuGiaoHangResponse;
import com.dantn.weblaptop.entity.hoadon.PhieuGiaoHang;

public class PhieuGiaoHangMapper {

    public static PhieuGiaoHang toEntity(CreatePhieuGiaoHang request ) {
        PhieuGiaoHang entity = new PhieuGiaoHang();
        entity.setMa(request.getMa());
        entity.setTrangThai(request.getTrangThai());
        entity.setTenNguoiNhan(request.getTenNguoiNhan());
        entity.setSdtNguoiNhan(request.getSdtNguoiNhan());
        entity.setDiaChiNhanHang(request.getDiaChiNhanHang());
        entity.setGhiChu(request.getGhiChu());
        entity.setChoXemHang(request.getChoXemHang());
        entity.setTienThuHo(request.getTienThuHo());
//        entity.setSerialNumberDaBans(request.getSerialNumberDaBans());
        return entity;
    }

    public static PhieuGiaoHangResponse toResponse(PhieuGiaoHang entity) {
        PhieuGiaoHangResponse response = new PhieuGiaoHangResponse();
        response.setId(entity.getId());
        response.setMa(entity.getMa());
        response.setTrangThai(entity.getTrangThai());
        response.setTenNguoiNhan(entity.getTenNguoiNhan());
        response.setSdtNguoiNhan(entity.getSdtNguoiNhan());
        response.setDiaChiNhanHang(entity.getDiaChiNhanHang());
        response.setGhiChu(entity.getGhiChu());
        response.setChoXemHang(entity.getChoXemHang());
        response.setTienThuHo(entity.getTienThuHo());
//        response.setSerialNumberDaBans(entity.getSerialNumberDaBans());
        return response;
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.util.GenerateCode;

public class HinhThucThanhToanMapper {

    public static HinhThucThanhToanResponse toHinhThucThanhToanResponse(HinhThucThanhToan hinhThucThanhToan) {
        HinhThucThanhToanResponse response = new HinhThucThanhToanResponse();
        response.setId(hinhThucThanhToan.getId());
        response.setMa(hinhThucThanhToan.getMa());
        response.setTen(hinhThucThanhToan.getTen());
        response.setTrangThai(hinhThucThanhToan.getTrangThai() == 0 ? "Hoạt Đông" : "Ngưng hoạt động");
        response.setMoTa(hinhThucThanhToan.getMoTa());
        response.setNgaySua(hinhThucThanhToan.getNgaySua());
        response.setNguoiSua(hinhThucThanhToan.getNguoiSua());
        response.setNgayTao(hinhThucThanhToan.getNgayTao());
        response.setNguoiTao(response.getNguoiTao());
        return response;
    }

    public static void update(UpdateHinhThucThanhToanRequest request, HinhThucThanhToan hinhThucThanhToan) {
        hinhThucThanhToan.setTen(request.getTen());
        hinhThucThanhToan.setMa(request.getMoTa());
        // ma , trang thai bỏ : thông tin ko đc update
    }

    public static HinhThucThanhToan create(CreateHinhThucThanhToanRequest request) {
        HinhThucThanhToan newPaymentMethod = new HinhThucThanhToan();
        newPaymentMethod.setMoTa(GenerateCode.generateHHTT());
        newPaymentMethod.setTen(request.getTen());
        newPaymentMethod.setMoTa(request.getMoTa());
        newPaymentMethod.setTrangThai(0);
        return newPaymentMethod;
    }
}

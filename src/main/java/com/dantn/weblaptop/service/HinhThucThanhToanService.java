package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonClientAccountRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonClientRequest;
import com.dantn.weblaptop.dto.request.create_request.GioHangChiTietRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HinhThucThanhToanService {
    HinhThucThanhToanResponse create(CreateHinhThucThanhToanRequest request);

    HinhThucThanhToanResponse update(Long id, UpdateHinhThucThanhToanRequest request) throws AppException;

    ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size);

    void updateStart(Long id);

    String payWithVNPAYOnline(List<GioHangChiTietRequest> cartDetail, HttpServletRequest request) throws AppException;

    String payWithVNPAYOnline2(CreateHoaDonClientRequest createHoaDonClientRequest, HoaDon hoaDon, HttpServletRequest request);
    String payWithVNPAYOnlineAdmin(String maHoaDon , HttpServletRequest request);

    String payWithVNPAYAccountOnline(CreateHoaDonClientAccountRequest createHoaDonClientRequest, HoaDon hoaDon, HttpServletRequest request);

    void handlePaymentCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

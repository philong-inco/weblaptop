package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HoaDonHinhThucThanhToanResponse;
import com.dantn.weblaptop.exception.AppException;

import java.util.List;

public interface HoaDonHinhThucThanhToanSerive {

    List<HoaDonHinhThucThanhToanResponse>getAllByBillCode (String billCode);
    HoaDonHinhThucThanhToanResponse create(CreateHoaDonHinhThucThanhToanRequest request , String billCode) throws AppException;
    void delete(Long id) throws AppException;
}

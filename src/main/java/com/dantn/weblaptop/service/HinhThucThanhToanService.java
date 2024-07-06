package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.exception.AppException;

import java.util.Optional;

public interface HinhThucThanhToanService {
    ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size);

    HinhThucThanhToan createPaymentMethods(CreateHinhThucThanhToanRequest request) ;

    HinhThucThanhToan updatePaymentMethods(Long id, UpdateHinhThucThanhToanRequest request) throws AppException;

    Boolean updateStatus(Long id);
}

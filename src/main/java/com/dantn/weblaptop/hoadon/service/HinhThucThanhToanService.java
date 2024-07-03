package com.dantn.weblaptop.hoadon.service;

import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.hoadon.dto.request.HinhThucThanhToanRequest;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;

import java.util.Optional;

public interface HinhThucThanhToanService {
    ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size);

    HinhThucThanhToan createPaymentMethods(HinhThucThanhToanRequest request) ;

    HinhThucThanhToan updatePaymentMethods(Long id, HinhThucThanhToanRequest request) throws AppException;

    Boolean updateStatus(Long id);
}

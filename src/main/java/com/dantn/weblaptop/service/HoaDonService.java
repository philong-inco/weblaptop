package com.dantn.weblaptop.hoadon.service;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.hoadon.dto.request.HoaDonRequest;
import com.dantn.weblaptop.hoadon.dto.response.HoaDonResponse;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;

import java.util.Optional;

public interface HoaDonService {
    ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size);

    HoaDonResponse createBill(HoaDonRequest request) throws AppException;

    HoaDonResponse updateBill(Long id, HoaDonRequest request);

    Boolean updateBillStatus(Long id, HoaDonStatus status);
}

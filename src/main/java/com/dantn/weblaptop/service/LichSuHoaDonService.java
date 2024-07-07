package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.exception.AppException;

import java.util.Optional;

public interface LichSuHoaDonService {
    LichSuHoaDonResponse create(CreateLichSuHoaDonRequest request) throws AppException;

    ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size);
}

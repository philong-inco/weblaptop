package com.dantn.weblaptop.hoadon.service;

import com.dantn.weblaptop.hoadon.dto.request.LichSuHoaDonRquest;
import com.dantn.weblaptop.hoadon.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;

import java.util.Optional;

public interface LichSuHoaDonService {
    LichSuHoaDonResponse create(LichSuHoaDonRquest request) throws AppException;

    ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size);
}

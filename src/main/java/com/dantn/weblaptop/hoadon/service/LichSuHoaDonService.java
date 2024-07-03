package com.dantn.weblaptop.hoadon.service;

import com.dantn.weblaptop.hoadon.dto.request.LichSuHoaDonRquest;
import com.dantn.weblaptop.hoadon.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;

import java.util.Optional;

public interface LichSuHoaDonService {
    LichSuHoaDonResponse create(LichSuHoaDonRquest request);

    ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size);

}

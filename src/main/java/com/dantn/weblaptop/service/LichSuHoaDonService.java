package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.LichSuHoaDonRquest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;

import java.util.Optional;

public interface LichSuHoaDonService {
    LichSuHoaDonResponse create(LichSuHoaDonRquest request);

    ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size);

}

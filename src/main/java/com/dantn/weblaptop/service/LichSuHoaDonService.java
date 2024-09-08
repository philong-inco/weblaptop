package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.RevertLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.exception.AppException;

import java.util.List;

public interface LichSuHoaDonService {
    LichSuHoaDonResponse create(CreateLichSuHoaDonRequest request) throws AppException;

    List<LichSuHoaDonResponse> getBillHistoryByBillId(Long billId);
    List<LichSuHoaDonResponse> getBillHistoryByBillCode(String code);

    void revertBillStatus(String codeBill) throws AppException;
}

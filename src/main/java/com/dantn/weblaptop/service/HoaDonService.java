package com.dantn.weblaptop.service;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.exception.AppException;

import java.util.List;
import java.util.Optional;

public interface HoaDonService {

    ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size);

    HoaDonResponse createBill() throws AppException;

    HoaDonResponse updateBill(Long id, UpdateHoaDonRequest request);

    List<HoaDonResponse> listBillByStatusAndType (String status , Integer type);

    void updateStatus(Long id , String status) throws AppException;
}

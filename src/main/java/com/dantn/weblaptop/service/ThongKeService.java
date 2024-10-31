package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.FindBillDateRequest;
import com.dantn.weblaptop.dto.response.ThongKeTrangThaiHoaDonResponse;

import java.util.List;

public interface ThongKeService {


    List<ThongKeTrangThaiHoaDonResponse> getAllStatisticalStatusBill(final FindBillDateRequest findBillDateRequest);

}

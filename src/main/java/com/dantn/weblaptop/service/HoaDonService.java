package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface HoaDonService {

    ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size);

    HoaDonResponse createBill() throws AppException;

    HoaDonResponse updateBill(Long id, UpdateHoaDonRequest request);

    HoaDonResponse getBillById (Long id) throws AppException;

    ResultPaginationResponse pageBillByStatusAndType (String status , Integer type, Optional<String> page, Optional<String> size);

    void updateStatus(Long id , String status) throws AppException;

    ResultPaginationResponse filterHoaDon (Specification<HoaDon> specification, Pageable pageable);
}

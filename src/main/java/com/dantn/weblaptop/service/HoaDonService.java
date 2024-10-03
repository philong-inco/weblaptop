package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Optional;

public interface HoaDonService {

    ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size);

    HoaDonResponse createBill() throws AppException;

    HoaDonResponse updateBill(Long id, UpdateHoaDonRequest request);

    HoaDonResponse updateBillByCode(String code, UpdateHoaDonRequest request);

    HoaDonResponse getBillById (Long id) throws AppException;

    HoaDonResponse getBillByCode (String code) throws AppException;

    HoaDonResponse getBillByIdAndStatus (Long id , String status);

    ResultPaginationResponse pageBillByStatusAndType (String status , Integer type, Optional<String> page, Optional<String> size);

    void updateStatus(String code , String status) throws AppException;


    ResultPaginationResponse filterHoaDon (Specification<HoaDon> specification, Pageable pageable);

    void deleteBillByCode (String code) ;

    HoaDonResponse addCustomerToBill (Long customerId, String billCode) throws AppException;

    HoaDonResponse addCouponToBill (Long couponId, String billCode) throws AppException;

    HoaDonResponse addCouponToBillByCode (String couponCode, String billCode) throws AppException;

    Boolean payCounter(String billCode, UpdateHoaDonRequest request) throws AppException;

    Long countBillByDate(Long startDate, Long endDate);

    BigDecimal sumBillByDate(Long startDate, Long endDate);
}

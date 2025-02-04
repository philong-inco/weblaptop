package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.SerialNumberDaBan_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberCodeDaBanRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.repository.HoaDonRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SerialNumberDaBanService {

    List<SerialNumberDaBanResponse> getSerialNumberDaBanPage(String code);

    Boolean create(CreateSerialNumberDaBanRequest request) throws AppException;
    Boolean createByProductCode(CreateSerialNumberCodeDaBanRequest request) throws AppException;
    void delete(SerialNumberSoldDelete request) throws AppException;

    List<SerialNumberDaBan_Dto> findSerialNumberDaBanTopSold(LocalDateTime startDate, LocalDateTime endDate) throws AppException;
}

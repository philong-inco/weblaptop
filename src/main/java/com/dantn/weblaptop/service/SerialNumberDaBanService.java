package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.repository.HoaDonRepository;

import java.math.BigDecimal;
import java.util.List;

public interface SerialNumberDaBanService {

    List<SerialNumberDaBanResponse> getSerialNumberDaBanPage(String code);

    Boolean create(CreateSerialNumberDaBanRequest request) throws AppException;

    void delete(SerialNumberSoldDelete request) throws AppException;

    BigDecimal getBigDecimal(HoaDon hoaDon, List<SerialNumberDaBanResponse> listSerialNumberDaBan, HoaDonRepository hoaDonRepository);

}

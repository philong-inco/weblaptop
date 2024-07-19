package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.exception.AppException;

import java.util.List;
import java.util.Optional;

public interface SerialNumberDaBanService {

    List<SerialNumberDaBanResponse> getSerialNumberDaBanPage (Long billId );

    SerialNumberDaBanResponse create(CreateSerialNumberDaBanRequest request) throws AppException;

    void  delete(Long id);

}

package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.response.ResultPaginationResponse;

import java.util.Optional;

public interface SerialNumberDaBanService {

    ResultPaginationResponse getSerialNumberDaBanPage (Long billId ,Optional<String> page , Optional<String> size);


}

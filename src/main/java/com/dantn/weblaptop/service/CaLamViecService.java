package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.response.CaLamViec_Response;

import java.util.List;

public interface CaLamViecService {
    List<CaLamViec_Response> getListCaLamViec();

    CaLamViec_Response findByIdCaLamViec(Long id);
}

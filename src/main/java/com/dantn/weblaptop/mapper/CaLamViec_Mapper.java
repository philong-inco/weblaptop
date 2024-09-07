package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.response.CaLamViec_Response;
import com.dantn.weblaptop.entity.nhanvien.CaLamViec;

import java.util.List;

public interface CaLamViec_Mapper {
    CaLamViec_Response EntiyToResponse(CaLamViec caLamViec);

    List<CaLamViec_Response> listCaLamViecEntityToCaLamViecResponse(List<CaLamViec> caLamViecList);
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.CaLamViec_Response;
import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.mapper.CaLamViec_Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CaLamViecMapper_Implement implements CaLamViec_Mapper {
    @Override
    public CaLamViec_Response EntiyToResponse(CaLamViec caLamViec) {
        if (caLamViec == null) {
            return null;
        }
        CaLamViec_Response response = new CaLamViec_Response();
        response.setId(caLamViec.getId());
        response.setCa(caLamViec.getCa());
        response.setMoTa(caLamViec.getMoTa());
        response.setTrangThai(caLamViec.getTrangThai());
        return response;
    }

    @Override
    public List<CaLamViec_Response> listCaLamViecEntityToCaLamViecResponse(List<CaLamViec> caLamViecList) {
        List<CaLamViec_Response> caLamViecResponseList = new ArrayList<>(caLamViecList.size());
        for (CaLamViec caLamViec : caLamViecList) {
            caLamViecResponseList.add(EntiyToResponse(caLamViec));
        }
        return caLamViecResponseList;
    }
}

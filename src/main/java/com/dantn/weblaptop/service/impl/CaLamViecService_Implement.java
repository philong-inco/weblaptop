package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.response.CaLamViec_Response;
import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.mapper.CaLamViec_Mapper;
import com.dantn.weblaptop.repository.CaLamViec_Repository;
import com.dantn.weblaptop.service.CaLamViecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CaLamViecService_Implement implements CaLamViecService {
    @Autowired
    CaLamViec_Repository caLamViecRepository;
    @Autowired
    CaLamViec_Mapper caLamViec_Mapper;

    @Override
    public List<CaLamViec_Response> getListCaLamViec() {
        List<CaLamViec> listCaLamViec = caLamViecRepository.findAll();
        List<CaLamViec_Response> responseList = new ArrayList<>();
        responseList = caLamViec_Mapper.listCaLamViecEntityToCaLamViecResponse(listCaLamViec);
        return responseList;
    }

    @Override
    public CaLamViec_Response findByIdCaLamViec(Long id) {
        CaLamViec caLamViec = caLamViecRepository.findById(id).get();
        CaLamViec_Response caLamViecResponse = new CaLamViec_Response();
        caLamViecResponse = caLamViec_Mapper.EntiyToResponse(caLamViec);
        return caLamViecResponse;
    }
}

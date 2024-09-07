package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateLichLamViec;
import com.dantn.weblaptop.dto.request.update_request.UpdateLichLamViec;
import com.dantn.weblaptop.dto.response.LichLamViecResponse;
import com.dantn.weblaptop.entity.nhanvien.LichLamViec;
import com.dantn.weblaptop.mapper.LichLamViec_Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LichLamViecMapper_Implement implements LichLamViec_Mapper {

    @Override
    public LichLamViec createLichLamViec(CreateLichLamViec createLichLamViec) {
        if (createLichLamViec == null) {
            return null;
        }

        LichLamViec lichLamViec = new LichLamViec();
        lichLamViec.setChuThich(createLichLamViec.getChuThich());
        lichLamViec.setNgayLamViec(createLichLamViec.getNgayLamViec());
        return lichLamViec;
    }

    @Override
    public LichLamViec updateLichLamViec(UpdateLichLamViec updateLichLamViec) {
        if (updateLichLamViec == null) {
            return null;
        }

        LichLamViec lichLamViec = new LichLamViec();
        lichLamViec.setChuThich(updateLichLamViec.getChuThich());
        lichLamViec.setNgayLamViec(updateLichLamViec.getNgayLamViec());
        return lichLamViec;
    }

    @Override
    public LichLamViecResponse entityToResponseLichLamViec(LichLamViec lichLamViec) {
        if (lichLamViec == null) {
            return null;
        }

        LichLamViecResponse response = new LichLamViecResponse();
        response.setChuThich(lichLamViec.getChuThich());
        response.setNgayLamViec(lichLamViec.getNgayLamViec());
        response.setNhanVien(lichLamViec.getNhanVien().getId());
        response.setCaLamViec(lichLamViec.getCaLamViec().getId());

        return response;
    }

    @Override
    public List<LichLamViecResponse> listLichLamViecToResponse(List<LichLamViec> listLichLamViec) {
        if (listLichLamViec == null || listLichLamViec.isEmpty()) {
            return new ArrayList<>();
        }

        List<LichLamViecResponse> responseList = new ArrayList<>();
        for (LichLamViec lichLamViec : listLichLamViec) {
            responseList.add(entityToResponseLichLamViec(lichLamViec));
        }

        return responseList;
    }
}

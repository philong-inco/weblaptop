package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateLichLamViec;
import com.dantn.weblaptop.dto.request.update_request.UpdateLichLamViec;
import com.dantn.weblaptop.dto.response.LichLamViecResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LichLamViec_Service {
    LichLamViecResponse createLichLamViec(CreateLichLamViec createLichLamViec);

    LichLamViecResponse updateLichLamViec(Long id, UpdateLichLamViec updateLichLamViec);

    List<LichLamViecResponse> getLichLamViecByIdNhanVien(Long id);

    List<LichLamViecResponse> getAllLichLamViec ();

    void deleteLichLamViec(Long id);
}

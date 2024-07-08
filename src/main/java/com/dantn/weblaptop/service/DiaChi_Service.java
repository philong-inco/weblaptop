package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiaChi_Service {
    DiaChi_Response createDiaChi(CreateDiaChi createDiaChi);

    DiaChi_Response updateDiaChi(Long id, UpdateDiaChi updateDiaChi);

    DiaChi_Response getDiaChiById(Long id);

    List<DiaChi_Response> getAllDiaChi();

    void deleteDiaChi(Long id);
}

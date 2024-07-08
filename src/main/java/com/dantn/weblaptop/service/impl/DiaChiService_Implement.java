package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.mapper.DiaChi_Mapper;
import com.dantn.weblaptop.repository.DiaChi_Repository;
import com.dantn.weblaptop.service.DiaChi_Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DiaChiService_Implement implements DiaChi_Service {

    private DiaChi_Repository diaChiRepository;
    private DiaChi_Mapper diaChiMapper;

    @Override
    public DiaChi_Response createDiaChi(CreateDiaChi createDiaChi) {
        DiaChi diaChi = diaChiMapper.CreateToEntityDiaChi(createDiaChi);
        DiaChi savedDiaChi = diaChiRepository.save(diaChi);
        return diaChiMapper.EntiyToResponse(savedDiaChi);
    }

    @Override
    public DiaChi_Response updateDiaChi(Long id, UpdateDiaChi updateDiaChi) {
        Optional<DiaChi> existingDiaChi = diaChiRepository.findById(id);
        if (existingDiaChi.isPresent()) {
            DiaChi diaChi = existingDiaChi.get();
            DiaChi updatedDiaChi = diaChiRepository.save(diaChi);
            return diaChiMapper.EntiyToResponse(updatedDiaChi);
        } else {
            throw new RuntimeException("DiaChi not found with id: " + id);
        }
    }

    @Override
    public DiaChi_Response getDiaChiById(Long id) {
        Optional<DiaChi> diaChi = diaChiRepository.findById(id);
        return diaChi.map(diaChiMapper::EntiyToResponse)
                .orElseThrow(() -> new RuntimeException("DiaChi not found with id: " + id));
    }

    @Override
    public Page<DiaChi> getAllDiaChi(Pageable pageable) {
        return diaChiRepository.pageAll(pageable);
    }

    @Override
    public void deleteDiaChi(Long id) {
        diaChiRepository.deleteById(id);
    }
}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateVaiTro_Request;
import com.dantn.weblaptop.dto.request.update_request.UpdateVaiTro_Request;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.mapper.VaiTro_Mapper;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import com.dantn.weblaptop.service.VaiTro_Service;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VaiTroService_Implement implements VaiTro_Service {

    private final VaiTro_Repository vaiTroRepository;

    private final VaiTro_Mapper vaiTroMapper;

    @Override
    @Transactional
    public VaiTro_Response create(CreateVaiTro_Request dto) {
        VaiTro vaiTro = vaiTroMapper.CreateToEntity(dto);
        vaiTro.setMa(GenerateCode.generateVaiTroCode());
        vaiTroRepository.save(vaiTro);
        return vaiTroMapper.toResponse(vaiTro);
    }

    @Override
    @Transactional
    public VaiTro_Response update(UpdateVaiTro_Request dto, Long id) {
        VaiTro existingVaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTro not found"));
        if(existingVaiTro != null){
            existingVaiTro.setTen(dto.getTen());
            existingVaiTro.setMoTa(dto.getMoTa());
            existingVaiTro.setTrangThai(dto.getTrangThai());
        }
        vaiTroRepository.save(existingVaiTro);
        return vaiTroMapper.toResponse(existingVaiTro);
    }

    @Override
    public VaiTro_Response getById(Long id) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTro not found"));
        return vaiTroMapper.toResponse(vaiTro);
    }

    @Override
    public List<VaiTro_Response> getAll() {
        return vaiTroRepository.findAll().stream()
                .map(vaiTroMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<VaiTro> pageAllVaiTro(Pageable pageable) {
        return vaiTroRepository.pageAll(pageable);
    }

    @Override
    public void revertStatus(Long id) {
        vaiTroRepository.revertStatus(0, id);
    }

    @Override
    public VaiTro_Response findVaiTroByIdNhanVienVaiTro(Long id) {
        VaiTro vaiTroByIdNhanVienVaiTro= vaiTroRepository.findVaiTroByIdNhanVienVaiTro(id);
        return vaiTroMapper.toResponse(vaiTroByIdNhanVienVaiTro);
    }
}

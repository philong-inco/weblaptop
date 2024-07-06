package com.dantn.weblaptop.VaiTro.Service.Implement;

import com.dantn.weblaptop.VaiTro.Dto.Request.CreateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Request.UpdateVaiTro_Request;
import com.dantn.weblaptop.VaiTro.Dto.Response.VaiTro_Response;
import com.dantn.weblaptop.VaiTro.Mapper.VaiTro_Mapper;
import com.dantn.weblaptop.VaiTro.Repository.VaiTro_Repository;
import com.dantn.weblaptop.VaiTro.Service.VaiTro_Service;
import com.dantn.weblaptop.constant.AccountStatus;
import com.dantn.weblaptop.constant.GenerateCode;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.hoadon.exception.AppException;
import com.dantn.weblaptop.nhanvien.Dto.Request.CreateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Request.UpdateNhanVien_Request;
import com.dantn.weblaptop.nhanvien.Dto.Response.NhanVien_Response;
import com.dantn.weblaptop.nhanvien.Repository.NhanVien_Repository;
import com.dantn.weblaptop.nhanvien.Service.NhanVien_Service;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    public VaiTro_Response update(UpdateVaiTro_Request dto) {
        VaiTro existingVaiTro = vaiTroRepository.findById(dto.getId())
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
}

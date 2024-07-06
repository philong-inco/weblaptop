package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.CPUCreate;
import com.dantn.weblaptop.dto.request.update_request.CPUUpdate;
import com.dantn.weblaptop.dto.response.CPUResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CPUMapper extends GenericsMapper<CPU, CPUCreate, CPUUpdate, CPUResponse> {
    @Override
    public CPU createToEntity(CPUCreate create) {
        CPU cpu = CPU.builder()
                .ten(create.getTen())
                .trangThai(create.getTrangThai())
                .dongCPU(create.getDongCPU())
                .nhaSanXuat(create.getNhaSanXuat())
                .build();
        return cpu;
    }

    @Override
    public CPU updateToEntity(CPUUpdate update, CPU entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        entity.setDongCPU(update.getDongCPU());
        entity.setNhaSanXuat(update.getNhaSanXuat());
        return entity;
    }

    @Override
    public CPUResponse entityToResponse(CPU entity) {
        CPUResponse response = CPUResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .dongCPU(entity.getDongCPU())
                .nhaSanXuat(entity.getNhaSanXuat())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<CPUResponse> listEntityToListResponse(List<CPU> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<CPUResponse> pageEntityToPageResponse(Page<CPU> entityPage) {
        List<CPUResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

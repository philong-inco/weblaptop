package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.HeDieuHanhCreate;
import com.dantn.weblaptop.dto.request.update_request.HeDieuHanhUpdate;
import com.dantn.weblaptop.dto.response.HeDieuHanhResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeDieuHanhMapper extends GenericsMapper<HeDieuHanh, HeDieuHanhCreate, HeDieuHanhUpdate, HeDieuHanhResponse> {
    @Override
    public HeDieuHanh createToEntity(HeDieuHanhCreate create) {
        HeDieuHanh heDieuHanh = HeDieuHanh.builder()
                .ten(create.getTen())
                .trangThai(create.getTrangThai())
                .build();
        return heDieuHanh;
    }

    @Override
    public HeDieuHanh updateToEntity(HeDieuHanhUpdate update, HeDieuHanh entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public HeDieuHanhResponse entityToResponse(HeDieuHanh entity) {
        HeDieuHanhResponse response = HeDieuHanhResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<HeDieuHanhResponse> listEntityToListResponse(List<HeDieuHanh> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<HeDieuHanhResponse> pageEntityToPageResponse(Page<HeDieuHanh> entityPage) {
        List<HeDieuHanhResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.VGACreate;
import com.dantn.weblaptop.dto.request.update_request.VGAUpdate;
import com.dantn.weblaptop.dto.response.VGAResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VGAMapper extends GenericsMapper<VGA, VGACreate, VGAUpdate, VGAResponse> {
    @Override
    public VGA createToEntity(VGACreate create) {
        VGA vga = VGA.builder()
                .ten(create.getTen())
                .dungLuong(create.getDungLuong())
                .trangThai(create.getTrangThai())
                .build();
        return vga;
    }

    @Override
    public VGA updateToEntity(VGAUpdate update, VGA entity) {
        entity.setTen(update.getTen());
        entity.setDungLuong(update.getDungLuong());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public VGAResponse entityToResponse(VGA entity) {
        VGAResponse response = VGAResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .dungLuong(entity.getDungLuong())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<VGAResponse> listEntityToListResponse(List<VGA> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<VGAResponse> pageEntityToPageResponse(Page<VGA> entityPage) {
        List<VGAResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

package com.dantn.weblaptop.sanpham.thuoctinh.mausac.mapper;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.mausac.dto.request.MauSacCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.mausac.dto.request.MauSacUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.mausac.dto.response.MauSacResponse;
import com.dantn.weblaptop.sanpham.thuoctinh.ocung.dto.response.OCungResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MauSacMapper extends GenericsMapper<MauSac, MauSacCreate, MauSacUpdate, MauSacResponse> {
    @Override
    public MauSac createToEntity(MauSacCreate create) {
        MauSac mauSac = MauSac.builder()
                .ten(create.getTen())
                .trangThai(create.getTrangThai())
                .build();
        return mauSac;
    }

    @Override
    public MauSac updateToEntity(MauSacUpdate update, MauSac entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public MauSacResponse entityToResponse(MauSac entity) {
        MauSacResponse response = MauSacResponse.builder()
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
    public List<MauSacResponse> listEntityToListResponse(List<MauSac> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<MauSacResponse> pageEntityToPageResponse(Page<MauSac> entityPage) {
        List<MauSacResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

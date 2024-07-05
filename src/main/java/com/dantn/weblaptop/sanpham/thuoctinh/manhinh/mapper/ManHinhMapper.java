package com.dantn.weblaptop.sanpham.thuoctinh.manhinh.mapper;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.response.ManHinhResponse;
import com.dantn.weblaptop.sanpham.thuoctinh.mausac.dto.response.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManHinhMapper extends GenericsMapper<ManHinh, ManHinhCreate, ManHinhUpdate, ManHinhResponse> {
    @Override
    public ManHinh createToEntity(ManHinhCreate create) {
        ManHinh manHinh = ManHinh.builder()
                .ten(create.getTen())
                .kichThuoc(create.getKichThuoc())
                .doPhanGiai(create.getDoPhanGiai())
                .trangThai(create.getTrangThai())
                .build();
        return manHinh;
    }

    @Override
    public ManHinh updateToEntity(ManHinhUpdate update, ManHinh entity) {
        entity.setTen(update.getTen());
        entity.setDoPhanGiai(update.getDoPhanGiai());
        entity.setKichThuoc(update.getKichThuoc());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public ManHinhResponse entityToResponse(ManHinh entity) {
        ManHinhResponse response = ManHinhResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .doPhanGiai(entity.getDoPhanGiai())
                .kichThuoc(entity.getKichThuoc())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<ManHinhResponse> listEntityToListResponse(List<ManHinh> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<ManHinhResponse> pageEntityToPageResponse(Page<ManHinh> entityPage) {
        List<ManHinhResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

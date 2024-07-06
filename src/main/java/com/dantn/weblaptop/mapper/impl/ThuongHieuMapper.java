package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThuongHieuMapper extends GenericsMapper<ThuongHieu, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> {
    @Override
    public ThuongHieu createToEntity(ThuongHieuCreate create) {
        ThuongHieu thuongHieu = ThuongHieu.builder()
                .ten(create.getTen())
                .moTa(create.getMoTa())
                .trangThai(create.getTrangThai())
                .build();
        return thuongHieu;
    }

    @Override
    public ThuongHieu updateToEntity(ThuongHieuUpdate update, ThuongHieu entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        entity.setMoTa(update.getMoTa());
        return entity;
    }

    @Override
    public ThuongHieuResponse entityToResponse(ThuongHieu entity) {
        ThuongHieuResponse response = ThuongHieuResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .moTa(entity.getMoTa())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<ThuongHieuResponse> listEntityToListResponse(List<ThuongHieu> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<ThuongHieuResponse> pageEntityToPageResponse(Page<ThuongHieu> entityPage) {
        List<ThuongHieuResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

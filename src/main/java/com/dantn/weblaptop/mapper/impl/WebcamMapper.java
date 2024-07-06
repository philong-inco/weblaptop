package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.WebcamCreate;
import com.dantn.weblaptop.dto.request.update_request.WebcamUpdate;
import com.dantn.weblaptop.dto.response.WebcamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebcamMapper extends GenericsMapper<Webcam, WebcamCreate, WebcamUpdate, WebcamResponse> {
    @Override
    public Webcam createToEntity(WebcamCreate create) {
        Webcam webcam = Webcam.builder()
                .doPhanGiai(create.getDoPhanGiai())
                .ten(create.getTen())
                .trangThai(create.getTrangThai())
                .build();
        return webcam;
    }

    @Override
    public Webcam updateToEntity(WebcamUpdate update, Webcam entity) {
        entity.setTen(update.getTen());
        entity.setDoPhanGiai(update.getDoPhanGiai());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public WebcamResponse entityToResponse(Webcam entity) {
        WebcamResponse response = WebcamResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .trangThai(entity.getTrangThai())
                .doPhanGiai(entity.getDoPhanGiai())
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
    public List<WebcamResponse> listEntityToListResponse(List<Webcam> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<WebcamResponse> pageEntityToPageResponse(Page<Webcam> entityPage) {
        List<WebcamResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.AnhSanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.AnhSanPhamUpdate;
import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.dto.response.BanPhimResponse;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.generics.IGenericsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnhSanPhamMapper implements IGenericsMapper<AnhSanPham, AnhSanPhamCreate, AnhSanPhamUpdate, AnhSanPhamResponse> {


    @Override
    public AnhSanPham createToEntity(AnhSanPhamCreate create) {
        AnhSanPham entity = AnhSanPham.builder()
                .url(create.getUrl())
                .trangThai(create.getStatus())
                .build();
        return entity;
    }

    @Override
    public AnhSanPham updateToEntity(AnhSanPhamUpdate update, AnhSanPham entity) {
        entity.setUrl(update.getUrl());
        entity.setTrangThai(update.getStatus());
        return entity;
    }

    @Override
    public AnhSanPhamResponse entityToResponse(AnhSanPham entity) {
        SanPhamChiTiet spct = entity.getSanPhamChiTiet();
        AnhSanPhamResponse response = AnhSanPhamResponse.builder()
                .id(entity.getId())
                .status(entity.getTrangThai())
                .tenBienThe(spct.getSanPham().getTen() + " | "
                        + spct.getCpu().getTen() + " | "
                        + spct.getRam().getTen() + " | "
                        + spct.getVga().getTen() + " | "
                        + spct.getBanPhim().getTen() + " | "
                        + spct.getManHinh().getTen() + " | "
                        + spct.getWebcam().getTen() + " | "
                        + spct.getHeDieuHanh().getTen() + " | "
                        + spct.getOCung().getTen() + " | "
                        + spct.getMauSac().getTen() + " | "
                )
                .idSPCT(spct.getId())
                .url(entity.getUrl())
                .build();

        return response;
    }

    @Override
    public List<AnhSanPhamResponse> listEntityToListResponse(List<AnhSanPham> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<AnhSanPhamResponse> pageEntityToPageResponse(Page<AnhSanPham> entityPage) {
        List<AnhSanPhamResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.service.SerialNumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SanPhamMapper extends GenericsMapper<SanPham, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {
    SerialNumberService serialNumberService;
    @Override
    public SanPham createToEntity(SanPhamCreate create) {
        SanPham sanPham = SanPham.builder()
                .ten(create.getTen())
                .moTa(create.getMoTa())
                .trangThai(create.getTrangThai())
                .build();
        return sanPham;
    }

    @Override
    public SanPham updateToEntity(SanPhamUpdate update, SanPham entity) {
        entity.setTen(update.getTen());
        entity.setMoTa(update.getMoTa());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public SanPhamResponse entityToResponse(SanPham entity) {
        SanPhamResponse response = SanPhamResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .moTa(entity.getMoTa())
                .trangThai(entity.getTrangThai())
                .nhuCau(entity.getNhuCau().getTen())
                .thuongHieu(entity.getThuongHieu().getTen())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();

        List<SerialNumberResponse> countSeriActive = serialNumberService.findAllByProductIdActive(entity.getId());
        int amount = (countSeriActive == null) ? 0 : countSeriActive.size();
        response.setAmount(amount+"");
        return response;
    }

    @Override
    public List<SanPhamResponse> listEntityToListResponse(List<SanPham> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamResponse> pageEntityToPageResponse(Page<SanPham> entityPage) {
        List<SanPhamResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

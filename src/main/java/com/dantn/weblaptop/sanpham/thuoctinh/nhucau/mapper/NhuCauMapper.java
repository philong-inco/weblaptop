package com.dantn.weblaptop.sanpham.thuoctinh.nhucau.mapper;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.response.NhuCauResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhuCauMapper extends GenericsMapper<NhuCau, NhuCauCreate, NhuCauUpdate, NhuCauResponse> {
    @Override
    public NhuCau createToEntity(NhuCauCreate create) {
        NhuCau nhuCau = NhuCau.builder()
                .ten(create.getTen())
                .moTa(create.getMoTa())
                .trangThai(create.getTrangThai())
                .build();
        return nhuCau;
    }

    @Override
    public NhuCau updateToEntity(NhuCauUpdate update, NhuCau entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        entity.setMoTa(update.getMoTa());
        return entity;
    }

    @Override
    public NhuCauResponse entityToResponse(NhuCau entity) {
        NhuCauResponse response = NhuCauResponse.builder()
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
    public List<NhuCauResponse> listEntityToListResponse(List<NhuCau> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<NhuCauResponse> pageEntityToPageResponse(Page<NhuCau> entityPage) {
        List<NhuCauResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

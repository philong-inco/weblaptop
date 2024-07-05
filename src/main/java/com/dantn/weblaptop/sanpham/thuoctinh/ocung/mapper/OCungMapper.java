package com.dantn.weblaptop.sanpham.thuoctinh.ocung.mapper;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.ocung.dto.request.OCungCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.ocung.dto.request.OCungUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.ocung.dto.response.OCungResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OCungMapper extends GenericsMapper<OCung, OCungCreate, OCungUpdate, OCungResponse> {
    @Override
    public OCung createToEntity(OCungCreate create) {
        OCung oCung = OCung.builder()
                .ten(create.getTen())
                .dungLuong(create.getDungLuong())
                .trangThai(create.getTrangThai())
                .build();
        return oCung;
    }

    @Override
    public OCung updateToEntity(OCungUpdate update, OCung entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        entity.setDungLuong(update.getDungLuong());
        return entity;
    }

    @Override
    public OCungResponse entityToResponse(OCung entity) {
        OCungResponse response = OCungResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .ten(entity.getTen())
                .dungLuong(entity.getDungLuong())
                .trangThai(entity.getTrangThai())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .trangThai(entity.getTrangThai())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<OCungResponse> listEntityToListResponse(List<OCung> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<OCungResponse> pageEntityToPageResponse(Page<OCung> entityPage) {
        List<OCungResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

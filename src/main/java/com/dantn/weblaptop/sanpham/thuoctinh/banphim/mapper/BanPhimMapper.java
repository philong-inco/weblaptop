package com.dantn.weblaptop.sanpham.thuoctinh.banphim.mapper;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.sanpham.generics.GenericsMapper;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.request.BanPhimCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.request.BanPhimUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.response.BanPhimResponse;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.response.CPUResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BanPhimMapper extends GenericsMapper<BanPhim, BanPhimCreate, BanPhimUpdate, BanPhimResponse> {
    @Override
    public BanPhim createToEntity(BanPhimCreate create) {
        BanPhim banPhim = BanPhim.builder()
                .ten(create.getTen())
                .trangThai(create.getTrangThai())
                .build();
        return banPhim;
    }

    @Override
    public BanPhim updateToEntity(BanPhimUpdate update, BanPhim entity) {
        entity.setTen(update.getTen());
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public BanPhimResponse entityToResponse(BanPhim entity) {
        BanPhimResponse response = BanPhimResponse.builder()
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
    public List<BanPhimResponse> listEntityToListResponse(List<BanPhim> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<BanPhimResponse> pageEntityToPageResponse(Page<BanPhim> entityPage) {
        List<BanPhimResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.generics.GenericsMapper;
import com.dantn.weblaptop.dto.request.create_request.SanPhamChiTietCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamChiTietUpdate;
import com.dantn.weblaptop.dto.response.SanPhamChiTietResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamChiTietMapper extends GenericsMapper<SanPhamChiTiet, SanPhamChiTietCreate, SanPhamChiTietUpdate, SanPhamChiTietResponse> {
    @Override
    public SanPhamChiTiet createToEntity(SanPhamChiTietCreate create) {
        SanPhamChiTiet spct = SanPhamChiTiet.builder()
                .giaBan(new BigDecimal(create.getGiaBan()))
                .trangThai(create.getTrangThai())
                .build();
        return spct;
    }

    @Override
    public SanPhamChiTiet updateToEntity(SanPhamChiTietUpdate update, SanPhamChiTiet entity) {
        entity.setGiaBan(new BigDecimal(update.getGiaBan()));
        entity.setTrangThai(update.getTrangThai());
        return entity;
    }

    @Override
    public SanPhamChiTietResponse entityToResponse(SanPhamChiTiet entity) {
        SanPhamChiTietResponse response = SanPhamChiTietResponse.builder()
                .id(entity.getId())
                .ma(entity.getMa())
                .giaBan(entity.getGiaBan() + "")
                .trangThai(entity.getTrangThai())
                .sanPham(entity.getSanPham().getTen())
                .ram(entity.getRam().getTen())
                .cpu(entity.getCpu().getTen())
                .webcam(entity.getWebcam().getTen())
                .banPhim(entity.getBanPhim().getTen())
                .heDieuHanh(entity.getHeDieuHanh().getTen())
                .manHinh(entity.getManHinh().getTen())
                .mauSac(entity.getMauSac().getTen())
                .oCung(entity.getOCung().getTen())
                .vga(entity.getVga().getTen())
                .ngayTao(entity.getNgayTao() + "")
                .ngaySua(entity.getNgaySua() + "")
                .nguoiTao(entity.getNguoiTao())
                .nguoiSua(entity.getNguoiSua())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<SanPhamChiTietResponse> listEntityToListResponse(List<SanPhamChiTiet> entityList) {
        return entityList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamChiTietResponse> pageEntityToPageResponse(Page<SanPhamChiTiet> entityPage) {
        List<SanPhamChiTietResponse> list = entityPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(list, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

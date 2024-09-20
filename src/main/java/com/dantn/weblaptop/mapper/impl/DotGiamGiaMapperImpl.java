package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.util.AutoSetTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DotGiamGiaMapperImpl implements DotGiamGiaMapper {
    private final AutoSetTime autoSetTime = new AutoSetTime();

    @Override
    public DotGiamGia createRequestToDotGiamGia(CreateDotGiamGiaRequest request) {
        DotGiamGia dotGiamGia = new DotGiamGia();
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setTrangThai(request.getTrangThai());
        dotGiamGia.setLoaiChietKhau(request.getLoaiChietKhau());
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetThuc());
        dotGiamGia.setGiaTriGiam(request.getGiaTriGiam());
        dotGiamGia.setNgayTao(autoSetTime.getCurrentTimes());
        return dotGiamGia;
    }

    @Override
    public DotGiamGiaResponse dotGiamGiaToDotGiamGiaResponse(DotGiamGia dotGiamGia) {
        DotGiamGiaResponse dotGiamGiaResponse = new DotGiamGiaResponse();
        dotGiamGiaResponse.setId(dotGiamGia.getId());
        dotGiamGiaResponse.setMa(dotGiamGia.getMa());
        dotGiamGiaResponse.setTen(dotGiamGia.getTen());
        dotGiamGiaResponse.setMoTa(dotGiamGia.getMoTa());
        dotGiamGiaResponse.setTrangThai(dotGiamGia.getTrangThai());
        dotGiamGiaResponse.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
        dotGiamGiaResponse.setThoiGianBatDau(dotGiamGia.getThoiGianBatDau());
        dotGiamGiaResponse.setThoiGianKetthuc(dotGiamGia.getThoiGianKetthuc());
        dotGiamGiaResponse.setGiaTriGiam(dotGiamGia.getGiaTriGiam());
        if(dotGiamGia.getDotGiamGiaSanPhamChiTiets() != null){
            Set<Long> spctIds = dotGiamGia.getDotGiamGiaSanPhamChiTiets().stream()
                    .map(spctDotGiamGia -> spctDotGiamGia.getSanPhamChiTiet().getId())
                    .collect(Collectors.toSet());
            dotGiamGiaResponse.setSpctDotGiamGias(spctIds);
        }
        return dotGiamGiaResponse;
    }

    public Page<DotGiamGiaResponse> findAllRequestToDotGiamGiaResponse(Page<DotGiamGia> page) {
        List<DotGiamGiaResponse> responses = page.stream().map(dotGiamGia -> {
            DotGiamGiaResponse dotGiamGiaResponse = new DotGiamGiaResponse();
            dotGiamGiaResponse.setId(dotGiamGia.getId());
            dotGiamGiaResponse.setMa(dotGiamGia.getMa());
            dotGiamGiaResponse.setTen(dotGiamGia.getTen());
            dotGiamGiaResponse.setMoTa(dotGiamGia.getMoTa());
            dotGiamGiaResponse.setTrangThai(dotGiamGia.getTrangThai());
            dotGiamGiaResponse.setThoiGianBatDau(dotGiamGia.getThoiGianBatDau());
            dotGiamGiaResponse.setThoiGianKetthuc(dotGiamGia.getThoiGianKetthuc());
            dotGiamGiaResponse.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
            dotGiamGiaResponse.setGiaTriGiam(dotGiamGia.getGiaTriGiam());
            return dotGiamGiaResponse;
        }).collect(Collectors.toList());
        return new PageImpl<>(responses, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
    }


    @Override
    public DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateGotGiamGiaRequest request) {
        dotGiamGia.setMa(request.getMa());
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setTrangThai(request.getTrangThai());
        dotGiamGia.setLoaiChietKhau(request.getLoaiChietKhau());
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetThuc());
        dotGiamGia.setGiaTriGiam(request.getGiaTriGiam());
        dotGiamGia.setNgayTao(autoSetTime.getCurrentTimes());
        return dotGiamGia;
    }
}

package com.dantn.weblaptop.dotgiamgia.model.mapper.impl;

import com.dantn.weblaptop.dotgiamgia.model.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaRequest;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.util.AutoSetTime;
import org.springframework.stereotype.Component;

@Component
public class DotGiamGiaMapperImpl implements DotGiamGiaMapper {
    private final AutoSetTime autoSetTime = new AutoSetTime();

    @Override
    public DotGiamGia createDotGiamGia(CreateDotGiamGiaRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        DotGiamGia dotGiamGia = new DotGiamGia();
        dotGiamGia.setMa(request.getMa());
        dotGiamGia.setTrangThai(request.getTrangThai());
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setLoaiChietKhau(request.getLoaiChietKhau());
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetthuc());
        dotGiamGia.setGiamToiDa(request.getGiamToiDa());
        dotGiamGia.setDotGiamGiaSanPhamChiTiets(request.getDotGiamGiaSanPhamChiTiets());
        dotGiamGia.setSerialNumberDaBans(request.getSerialNumberDaBans());
        return dotGiamGia;
    }

    @Override
    public DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateDotGiamGiaRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        dotGiamGia.setMa(request.getMa());
        dotGiamGia.setTrangThai(request.getTrangThai());
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setLoaiChietKhau(request.getLoaiChietKhau());
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetthuc());
        dotGiamGia.setGiamToiDa(request.getGiamToiDa());
        dotGiamGia.setDotGiamGiaSanPhamChiTiets(request.getDotGiamGiaSanPhamChiTiets());
        dotGiamGia.setSerialNumberDaBans(request.getSerialNumberDaBans());
        dotGiamGia.setNgayTao(autoSetTime.getCurrentTimes());
        dotGiamGia.setNgaySua(autoSetTime.getCurrentTimes());
        return dotGiamGia;
    }
}

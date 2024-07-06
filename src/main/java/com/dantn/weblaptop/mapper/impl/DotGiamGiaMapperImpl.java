package com.dantn.weblaptop.mapper.impl;

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
import java.util.stream.Collectors;

@Component
public class DotGiamGiaMapperImpl implements DotGiamGiaMapper {
    private final AutoSetTime autoSetTime = new AutoSetTime();

    @Override
    public DotGiamGia createRequestToDotGiamGia(CreateDotGiamGiaRequest request) {
        DotGiamGia dotGiamGia = new DotGiamGia();
        dotGiamGia.setMa(request.getMa());
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setTrangThai(Integer.parseInt(request.getTrangThai()));
        dotGiamGia.setLoaiChietKhau(Integer.parseInt(request.getLoaiChietKhau()));
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetthuc());
        // Ép kiểu từ String sang BigDecimal
        BigDecimal giamToiDa = new BigDecimal(request.getGiamToiDa());

        // Thiết lập giá trị cho đối tượng dotGiamGia
        dotGiamGia.setGiamToiDa(giamToiDa);
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
        dotGiamGiaResponse.setGiamToiDa(dotGiamGia.getGiamToiDa());
        dotGiamGiaResponse.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
        dotGiamGiaResponse.setThoiGianBatDau(dotGiamGia.getThoiGianBatDau());
        dotGiamGiaResponse.setThoiGianKetthuc(dotGiamGia.getThoiGianKetthuc());
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
            dotGiamGiaResponse.setGiamToiDa(dotGiamGia.getGiamToiDa());
            dotGiamGiaResponse.setLoaiChietKhau(dotGiamGia.getLoaiChietKhau());
            return dotGiamGiaResponse;
        }).collect(Collectors.toList());
        return new PageImpl<>(responses, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
    }

    @Override
    public DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateGotGiamGiaRequest request) {
        dotGiamGia.setMa(request.getMa());
        dotGiamGia.setTen(request.getTen());
        dotGiamGia.setMoTa(request.getMoTa());
        dotGiamGia.setTrangThai(Integer.parseInt(request.getTrangThai()));
        dotGiamGia.setLoaiChietKhau(Integer.parseInt(request.getLoaiChietKhau()));
        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetthuc());
        // Ép kiểu từ String sang BigDecimal
        BigDecimal giamToiDa = new BigDecimal(request.getGiamToiDa());

        // Thiết lập giá trị cho đối tượng dotGiamGia
        dotGiamGia.setGiamToiDa(giamToiDa);
        dotGiamGia.setNgayTao(autoSetTime.getCurrentTimes());
        return dotGiamGia;
    }


//    @Override
//    public DotGiamGia updateDotGiamGia(DotGiamGia dotGiamGia, UpdateDotGiamGiaRequest request) {
//        if (request == null) {
//            throw new IllegalArgumentException("Request cannot be null");
//        }
//        dotGiamGia.setMa(request.getMa());
//        dotGiamGia.setTrangThai(request.getTrangThai());
//        dotGiamGia.setTen(request.getTen());
//        dotGiamGia.setMoTa(request.getMoTa());
//        dotGiamGia.setLoaiChietKhau(request.getLoaiChietKhau());
//        dotGiamGia.setThoiGianBatDau(request.getThoiGianBatDau());
//        dotGiamGia.setThoiGianKetthuc(request.getThoiGianKetthuc());
//        dotGiamGia.setGiamToiDa(request.getGiamToiDa());
//        dotGiamGia.setDotGiamGiaSanPhamChiTiets(request.getDotGiamGiaSanPhamChiTiets());
//        dotGiamGia.setSerialNumberDaBans(request.getSerialNumberDaBans());
//        dotGiamGia.setNgayTao(autoSetTime.getCurrentTimes());
//        dotGiamGia.setNgaySua(autoSetTime.getCurrentTimes());
//        return dotGiamGia;
//    }
}

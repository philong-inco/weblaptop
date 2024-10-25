package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.constant.RankCustomer;
import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.mapper.KhachHang_Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class KhachHangMapper_Implement implements KhachHang_Mapper {

    @Override
    public KhachHang createToEntityKhachHang(CreateKhachHang createKhachHang) {
        if (createKhachHang == null) {
            return null;
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setMa(createKhachHang.getMa());
        khachHang.setTen(createKhachHang.getTen());
        khachHang.setEmail(createKhachHang.getEmail());
        khachHang.setSdt(createKhachHang.getSdt());
        khachHang.setNgaySinh(createKhachHang.getNgaySinh());
        khachHang.setGioiTinh(createKhachHang.getGioiTinh());
        khachHang.setHinhAnh(createKhachHang.getHinhAnh());
        return khachHang;
    }

    @Override
    public KhachHang updateToEntityKhachHang(UpdateKhachHang updateKhachHang) {
        if (updateKhachHang == null) {
            return null;
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setTen(updateKhachHang.getTen());
        khachHang.setEmail(updateKhachHang.getEmail());
        khachHang.setSdt(updateKhachHang.getSdt());
        khachHang.setNgaySinh(updateKhachHang.getNgaySinh());
        khachHang.setGioiTinh(updateKhachHang.getGioiTinh());
        khachHang.setHinhAnh(updateKhachHang.getHinhAnh());
        return khachHang;
    }

    @Override
    public KhachHangResponse entityToResponseKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            return null;
        }

        return KhachHangResponse.builder()
                .id(khachHang.getId())
                .ma(khachHang.getMa())
                .trangThai(khachHang.getTrangThai())
                .ten(khachHang.getTen())
                .email(khachHang.getEmail())
                .sdt(khachHang.getSdt())
                .ngaySinh(khachHang.getNgaySinh())
                .gioiTinh(khachHang.getGioiTinh())
                .hinhAnh(khachHang.getHinhAnh())
                .hangKhachHang(khachHang.getHangKhachHang())
                .sessionId(khachHang.getSessionId())
                .tienGiamHang(RankCustomer.getValueByRank(khachHang.getHangKhachHang()))
                .build();
    }

    @Override
    public List<KhachHangResponse> listKhachHangToKhachHangResponse(List<KhachHang> khachHangList) {
        if (khachHangList == null) {
            return null;
        }

        return khachHangList.stream()
                .map(this::entityToResponseKhachHang)
                .collect(Collectors.toList());
    }
}

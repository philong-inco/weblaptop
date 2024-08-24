package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.mapper.DiaChi_Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DiaChiMapper_Implement implements DiaChi_Mapper {
    @Override
    public DiaChi CreateToEntityDiaChi(CreateDiaChi dto) {
        if (dto == null) {
            return null;
        }
        DiaChi diaChi = new DiaChi();
        diaChi.setTrangThai(dto.getTrangThai());
        diaChi.setLoaiDiaChi(dto.getLoaiDiaChi());
        diaChi.setTenNguoiNhan(dto.getTenNguoiNhan());
        diaChi.setSdtNguoiNhan(dto.getSdtNguoiNhan());
        diaChi.setEmailNguoiNhan(dto.getEmailNguoiNhan());
        diaChi.setDiaChiNhanHang(dto.getDiaChiNhanHang());
        diaChi.setIdPhuongXa(dto.getIdPhuongXa());
        diaChi.setIdQuanHuyen(dto.getIdQuanHuyen());
        diaChi.setIdTinhThanhPho(dto.getIdTinhThanhPho());
        return diaChi;
    }

    @Override
    public DiaChi UpdateToEntity(UpdateDiaChi dto) {
        if (dto == null) {
            return null;
        }

        DiaChi diaChi = new DiaChi();
        diaChi.setTenNguoiNhan(dto.getTenNguoiNhan());
        diaChi.setSdtNguoiNhan(dto.getSdtNguoiNhan());
        diaChi.setEmailNguoiNhan(dto.getEmailNguoiNhan());
        diaChi.setDiaChiNhanHang(dto.getDiaChiNhanHang());
        diaChi.setIdPhuongXa(dto.getIdPhuongXa());
        diaChi.setIdQuanHuyen(dto.getIdQuanHuyen());
        diaChi.setIdTinhThanhPho(dto.getIdTinhThanhPho());
        return diaChi;
    }

    @Override
    public DiaChi_Response EntiyToResponse(DiaChi entity) {
        if (entity == null) {
            return null;
        }

        return DiaChi_Response.builder()
                .id(entity.getId())
                .trangThai(entity.getTrangThai())
                .loaiDiaChi(entity.getLoaiDiaChi())
                .tenNguoiNhan(entity.getTenNguoiNhan())
                .sdtNguoiNhan(entity.getSdtNguoiNhan())
                .emailNguoiNhan(entity.getEmailNguoiNhan())
                .diaChiNhanHang(entity.getDiaChiNhanHang())
                .idPhuongXa(entity.getIdPhuongXa())
                .idQuanHuyen(entity.getIdPhuongXa())
                .idTinhThanhPho(entity.getIdTinhThanhPho())
                .build();
    }

    @Override
    public List<DiaChi_Response> listNhanVienEntityToNhanVienResponse(List<DiaChi> diaChiList) {
        if (diaChiList == null || diaChiList.isEmpty()) {
            return List.of();
        }

        return diaChiList.stream()
                .map(this::EntiyToResponse)
                .collect(Collectors.toList());
    }
}


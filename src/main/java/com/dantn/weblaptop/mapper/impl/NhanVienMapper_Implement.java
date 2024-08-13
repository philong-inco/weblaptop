package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.mapper.NhanVien_Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class NhanVienMapper_Implement implements NhanVien_Mapper {

    @Override
    public NhanVien CreateToEntity(CreateNhanVien dto) {
        if (dto == null) {
            return null;
        }
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMa(dto.getMa());
        nhanVien.setTrangThai(dto.getTrang_thai());
        nhanVien.setTen(dto.getTen());
        nhanVien.setEmail(dto.getEmail());
        nhanVien.setMatKhau(dto.getMatKhau());
        nhanVien.setSdt(dto.getSdt());
        nhanVien.setNgaySinh(dto.getNgaySinh());
        nhanVien.setGioiTinh(dto.getGioiTinh());
        nhanVien.setHinhAnh(dto.getHinhAnh());

        nhanVien.setNgayThoiViec(dto.getNgayThoiViec());
        nhanVien.setDiaChi(dto.getDiaChi());
        return nhanVien;
    }

    @Override
    public NhanVien UpdateToEntity(UpdateNhanVien dto) {
        if (dto == null) {
            return null;
        }
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMa(dto.getMa());
        nhanVien.setTen(dto.getTen());
        nhanVien.setEmail(dto.getEmail());
        nhanVien.setMatKhau(dto.getMatKhau());
        nhanVien.setSdt(dto.getSdt());
        nhanVien.setNgaySinh(dto.getNgaySinh());
        nhanVien.setGioiTinh(dto.getGioiTinh());
        nhanVien.setHinhAnh(dto.getHinhAnh());
        nhanVien.setDiaChi(dto.getDiaChi());
        return nhanVien;
    }

    @Override
    public NhanVienResponse EntiyToResponse(NhanVien entity) {
        if (entity == null) {
            return null;
        }
        NhanVienResponse response = new NhanVienResponse();
        response.setId(entity.getId());
        response.setMa(entity.getMa());
        response.setTrangThai(entity.getTrangThai());
        response.setTen(entity.getTen());
        response.setEmail(entity.getEmail());
        response.setSdt(entity.getSdt());
        response.setNgaySinh(entity.getNgaySinh());
        response.setGioiTinh(entity.getGioiTinh());
        response.setHinhAnh(entity.getHinhAnh());

        response.setNgayBatDauLamViec(entity.getNgayBatDauLamViec());
        response.setNgayThoiViec(entity.getNgayThoiViec());
        response.setDiaChi(entity.getDiaChi());
        return response;
    }

    @Override
    public List<NhanVienResponse> listNhanVienEntityToNhanVienResponse(List<NhanVien> nhanVienList) {
        List<NhanVienResponse> list = new ArrayList<>(nhanVienList.size());
        for (NhanVien nhanVien : nhanVienList) {
            list.add(EntiyToResponse(nhanVien));
        }
        return list;
    }

}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.mapper.DiaChi_Mapper;
import com.dantn.weblaptop.repository.DiaChi_Repository;
import com.dantn.weblaptop.service.DiaChi_Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DiaChiService_Implement implements DiaChi_Service {

    private DiaChi_Repository diaChiRepository;
    private DiaChi_Mapper diaChiMapper;

    @Override
    public DiaChi_Response createDiaChi(CreateDiaChi createDiaChi) {
        DiaChi diaChi = diaChiMapper.CreateToEntityDiaChi(createDiaChi);
        DiaChi savedDiaChi = diaChiRepository.save(diaChi);
        return diaChiMapper.EntiyToResponse(savedDiaChi);
    }

    @Override
    public DiaChi_Response updateDiaChi(Long id, UpdateDiaChi updateDiaChi) {
        Optional<DiaChi> existingDiaChi = diaChiRepository.findById(id);
        if (existingDiaChi.isPresent()) {
            DiaChi diaChi = existingDiaChi.get();

            // Cập nhật các thuộc tính của diaChi bằng giá trị từ updateDiaChi
            diaChi.setTenNguoiNhan(updateDiaChi.getTenNguoiNhan());
            diaChi.setSdtNguoiNhan(updateDiaChi.getSdtNguoiNhan());
            diaChi.setDiaChiNhanHang(updateDiaChi.getDiaChiNhanHang());
            diaChi.setEmailNguoiNhan(updateDiaChi.getEmailNguoiNhan());
            diaChi.setIdTinhThanhPho(updateDiaChi.getIdTinhThanhPho());
            diaChi.setIdQuanHuyen(updateDiaChi.getIdQuanHuyen());
            diaChi.setIdPhuongXa(updateDiaChi.getIdPhuongXa());

            // Lưu các thay đổi
            DiaChi updatedDiaChi = diaChiRepository.save(diaChi);

            // Chuyển đổi entity sang response DTO và trả về
            return diaChiMapper.EntiyToResponse(updatedDiaChi);
        } else {
            throw new RuntimeException("DiaChi not found with id: " + id);
        }
    }


    @Override
    public DiaChi_Response getDiaChiById(Long id) {
        Optional<DiaChi> diaChi = diaChiRepository.findById(id);
        return diaChi.map(diaChiMapper::EntiyToResponse)
                .orElseThrow(() -> new RuntimeException("DiaChi not found with id: " + id));
    }

    @Override
    public List<DiaChi_Response> getAllDiaChiByIdKhachHang(Long idKhachHang) {
        List<DiaChi> diaChiList = diaChiRepository.findByKhachHangId(idKhachHang);
        return diaChiList.stream()
                .map(this::entityToResponseDiaChi)
                .collect(Collectors.toList());
    }
    private DiaChi_Response entityToResponseDiaChi(DiaChi diaChi) {
        if (diaChi == null) {
            return null;
        }
        return DiaChi_Response.builder()
                .id(diaChi.getId())
                .trangThai(diaChi.getTrangThai())
                .loaiDiaChi(diaChi.getLoaiDiaChi())
                .tenNguoiNhan(diaChi.getTenNguoiNhan())
                .sdtNguoiNhan(diaChi.getSdtNguoiNhan())
                .emailNguoiNhan(diaChi.getEmailNguoiNhan())
                .idQuanHuyen(diaChi.getIdQuanHuyen())
                .idPhuongXa(diaChi.getIdPhuongXa())
                .idTinhThanhPho(diaChi.getIdTinhThanhPho())
                .diaChiNhanHang(diaChi.getDiaChiNhanHang())
                .build();
    }


    @Override
    public void deleteDiaChi(Long id) {
        diaChiRepository.deleteById(id);
    }

    @Override
    public void defauldiaChi(Long id, long idKhachHang) {
        diaChiRepository.defaultDiaChi(id, idKhachHang);
    }
}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateLichLamViec;
import com.dantn.weblaptop.dto.request.update_request.UpdateLichLamViec;
import com.dantn.weblaptop.dto.response.LichLamViecResponse;
import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.LichLamViec;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.mapper.LichLamViec_Mapper;
import com.dantn.weblaptop.repository.CaLamViec_Repository;
import com.dantn.weblaptop.repository.LichLamViec_Repository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.service.LichLamViec_Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LichLamViecService_Implement implements LichLamViec_Service {
    private final LichLamViec_Repository lichLamViec_Repository;
    private final LichLamViec_Mapper lichLamViec_Mapper;
    private final NhanVien_Repositoy nhanVienRepositoy;
    private final CaLamViec_Repository caLamViecRepository;

    @Override
    public LichLamViecResponse createLichLamViec(CreateLichLamViec createLichLamViec) {
        try {
            LichLamViec lamViecSave = new LichLamViec();
            if (createLichLamViec != null) {
                LichLamViec lichLamViec = lichLamViec_Mapper.createLichLamViec(createLichLamViec);
                lichLamViec.setChuThich(createLichLamViec.getChuThich());
                lichLamViec.setNgayTao(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
                NhanVien getNhanVien = nhanVienRepositoy.findByIdNhanVien(createLichLamViec.getIdNhanVien());
                if (getNhanVien != null) {
                    lichLamViec.setNhanVien(getNhanVien);
                }
                CaLamViec caLamViecs = caLamViecRepository.findByIdCaLamViec(createLichLamViec.getIdCaLamViec());
                if (caLamViecs != null) {
                    lichLamViec.setCaLamViec(caLamViecs);
                }
                lamViecSave = lichLamViec_Repository.save(lichLamViec);
            }
            return lichLamViec_Mapper.entityToResponseLichLamViec(lamViecSave);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create khach hang. Possibly duplicate record." + e);
        }
    }

    @Override
    public LichLamViecResponse updateLichLamViec(Long id, UpdateLichLamViec updateLichLamViec) {
        try {
            LichLamViec lichLamViec = lichLamViec_Repository.findById(id).get();
            LichLamViec lamViecSave = null;
            if (updateLichLamViec != null) {
                lichLamViec.setChuThich(updateLichLamViec.getChuThich());
                NhanVien getNhanVien = nhanVienRepositoy.findByIdNhanVien(updateLichLamViec.getIdNhanVien());
                if (getNhanVien != null) {
                    lichLamViec.setNhanVien(getNhanVien);
                }
                CaLamViec caLamViecs = caLamViecRepository.findByIdCaLamViec(updateLichLamViec.getIdCaLamViec());
                if (caLamViecs != null) {
                    lichLamViec.setCaLamViec(caLamViecs);
                }
                lamViecSave = lichLamViec_Repository.save(lichLamViec);
            }
            return lichLamViec_Mapper.entityToResponseLichLamViec(lamViecSave);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update lich lam viec. Possibly duplicate record." + e);
        }
    }

    @Override
    public List<LichLamViecResponse> getLichLamViecByIdNhanVien(Long id) {
        List<LichLamViec> lichLamViec = new ArrayList<>();
        if (id != null) {
            lichLamViec = lichLamViec_Repository.findLichLamViecByIdNhanVien(id);
        }
        return lichLamViec_Mapper.listLichLamViecToResponse(lichLamViec);
    }

    @Override
    public List<LichLamViecResponse> getAllLichLamViec() {
        List<LichLamViec> lichLamViec = lichLamViec_Repository.findAll();
        return lichLamViec_Mapper.listLichLamViecToResponse(lichLamViec);
    }

    @Override
    public void deleteLichLamViec(Long id) {
        lichLamViec_Repository.deleteTrangThai(id);
    }
}

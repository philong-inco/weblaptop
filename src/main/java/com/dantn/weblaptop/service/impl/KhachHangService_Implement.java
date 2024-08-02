package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.InfomationKhachHang;
import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.mapper.KhachHang_Mapper;
import com.dantn.weblaptop.repository.DiaChi_Repository;
import com.dantn.weblaptop.repository.KhachHang_Repository;
import com.dantn.weblaptop.service.KhachHang_Service;
import com.dantn.weblaptop.util.GenerateCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class KhachHangService_Implement implements KhachHang_Service {

    @Autowired
    KhachHang_Repository khachHangRepository;

    @Autowired
    DiaChi_Repository diaChiRepository;

    @Autowired
    KhachHang_Mapper khachHangMapper;

    @Override
    public Page<KhachHangResponse> pageKhachHang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);
        return khachHangPage.map(khachHangMapper::entityToResponseKhachHang);
    }

    @Override
    public Page<KhachHangResponse> pageSearchGioiTinh(Integer pageNo, Integer size, Integer gioiTinh) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KhachHang> khachHangPage = khachHangRepository.pageSearchGioiTinh(pageable,gioiTinh);
        return khachHangPage.map(khachHangMapper::entityToResponseKhachHang);
    }

    @Override
    public Page<KhachHangResponse> pageSearchKhachHang(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KhachHang> khachHangPage = khachHangRepository.pageSearch(pageable, search);
        return khachHangPage.map(khachHangMapper::entityToResponseKhachHang);
    }

    @Override
    public List<KhachHangResponse> listKhachHangResponse() {
        return khachHangMapper.listKhachHangToKhachHangResponse(khachHangRepository.findAll());

    }

    @Override
    public List<InfomationKhachHang> listKhachHangInfo() {
        List<KhachHang> khachHangEntities = khachHangRepository.findAll();
        List<InfomationKhachHang> khachHangInfos = new ArrayList<>();
        for (KhachHang khachHangEntity : khachHangEntities) {
            InfomationKhachHang khachHangInfo = new InfomationKhachHang();
            khachHangInfo.setTen(khachHangEntity.getTen());
            khachHangInfo.setGioiTinh(khachHangEntity.getGioiTinh());
            khachHangInfo.setNgaySinh(khachHangEntity.getNgaySinh());
            khachHangInfo.setEmail(khachHangEntity.getEmail());
            khachHangInfo.setSdt(khachHangEntity.getSdt());
            khachHangInfos.add(khachHangInfo);
        }
        return khachHangInfos;
    }

    @Override
    public KhachHangResponse create(CreateKhachHang createKhachHangRequest, HttpServletRequest request) {
        try {
            KhachHang khachHang = khachHangMapper.createToEntityKhachHang(createKhachHangRequest);
            khachHang.setMa(GenerateCode.generateKhachHangCode());
            khachHang.setNgayTao(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
            khachHang.setTrangThai(1);
            // Lấy session ID từ request
            String sessionId = request.getSession().getId();
            khachHang.setSessionId(sessionId);

            if (khachHangRepository.findKhachHangBySdt(khachHang.getSdt()) != null) {
                throw new RuntimeException("Số điện thoại này đã tồn tại " + khachHang.getSdt());
            }

            if (khachHangRepository.findKhachHangByEmail(khachHang.getEmail()) != null) {
                throw new RuntimeException("Email này đã tồn tại " + khachHang.getEmail());
            }
            KhachHang khSave = khachHangRepository.save(khachHang);
            if(khSave != null){
                DiaChi diaChi = new DiaChi();
                diaChi.setTenNguoiNhan(createKhachHangRequest.getTen());
                diaChi.setSdtNguoiNhan(createKhachHangRequest.getSdt());
                diaChi.setEmailNguoiNhan(createKhachHangRequest.getEmail());
                diaChi.setIdTinhThanhPho(createKhachHangRequest.getIdTinhThanhPho());
                diaChi.setIdQuanHuyen(createKhachHangRequest.getIdQuanHuyen());
                diaChi.setIdPhuongXa(createKhachHangRequest.getIdPhuongXa());
                diaChi.setSdtNguoiNhan(createKhachHangRequest.getSdt());
                diaChi.setDiaChiNhanHang(createKhachHangRequest.getDiaChiNhanHang());
                diaChi.setLoaiDiaChi(1);
                diaChi.setKhachHang(khSave);
                diaChiRepository.save(diaChi);
            }
            return khachHangMapper.entityToResponseKhachHang(khSave);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create khach hang. Possibly duplicate record." + ex);
        }
    }

    @Override
    public KhachHangResponse findKhachHangByEmail(String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        return khachHangMapper.entityToResponseKhachHang(khachHang);
    }

    @Override
    public boolean checkMail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return khachHangRepository.existsByEmail(email);
    }

    @Override
    public KhachHangResponse update(UpdateKhachHang updateKhachHangRequest, Long id) {
        try {
            KhachHang khachHang = khachHangRepository.findKhachHangById(id);
            if (khachHang != null) {
                KhachHang updateKhachHang = khachHangMapper.updateToEntityKhachHang(updateKhachHangRequest);

                for (KhachHang kh : khachHangRepository.findAll()) {
                    if (kh.getEmail().equals(updateKhachHang.getEmail()) && kh.getId() != khachHang.getId()) {
                        throw new RuntimeException("Email này đã tồn tại " + kh.getEmail());
                    }
                }

                for (KhachHang kh : khachHangRepository.findAll()) {
                    if (kh.getSdt().equals(updateKhachHang.getSdt()) && kh.getId() != khachHang.getId()) {
                        throw new RuntimeException("Số điện thoại này đã tồn tại " + kh.getSdt());
                    }
                }
                khachHang.setHo(updateKhachHang.getHo());
                khachHang.setTen(updateKhachHang.getTen());
                khachHang.setHinhAnh(updateKhachHang.getHinhAnh());
                khachHang.setGioiTinh(updateKhachHang.getGioiTinh());
                khachHang.setNgaySinh(updateKhachHang.getNgaySinh());
                khachHang.setEmail(updateKhachHang.getEmail());
                khachHang.setSdt(updateKhachHang.getSdt());
                khachHang.setNgaySua(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

                return khachHangMapper.entityToResponseKhachHang(khachHangRepository.save(khachHang));
            }

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to update khach hang. Possibly duplicate record." + ex);
        }
        return null;
    }

    @Override
    public KhachHangResponse getOne(Long id) {
        KhachHang khachHang = khachHangRepository.findKhachHangById(id);
        return khachHangMapper.entityToResponseKhachHang(khachHang);
    }

    @Override
    public void removeOrRevert(Integer trangThaiKhachHang, Long id) {
        khachHangRepository.removeOrRevert(trangThaiKhachHang, id);
    }


    @Override
    public boolean suaThongTin(InfomationKhachHang infomationKhachHang, Long id) {
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findById(id);
        if (optionalKhachHang.isPresent()) {
            KhachHang khachHang = optionalKhachHang.get();
            khachHang.setTen(infomationKhachHang.getTen());
            khachHang.setSdt(infomationKhachHang.getSdt());
            khachHang.setEmail(infomationKhachHang.getEmail());
            khachHang.setNgaySinh(infomationKhachHang.getNgaySinh());
            khachHang.setGioiTinh(infomationKhachHang.getGioiTinh());
            khachHangRepository.save(khachHang);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean changeEmail(InfomationKhachHang infomationKhachHang, String newEmail) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(infomationKhachHang.getEmail());
        if (khachHang == null) {
            return false;
        }
        if (khachHang != null) {
            khachHang.setEmail(newEmail);
            khachHangRepository.save(khachHang);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateImage(String image, String email) {
        khachHangRepository.updateImageKhachHang(image, email);
    }
}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.EmailSender;
import com.dantn.weblaptop.dto.ChangeEmail_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.NhanVienVaiTro;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.mapper.NhanVien_Mapper;
import com.dantn.weblaptop.repository.NhanVienVaiTroRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import com.dantn.weblaptop.service.NhanVien_Service;
import com.dantn.weblaptop.util.GenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Component
public class NhanVienService_Implement implements NhanVien_Service {
    @Autowired
    NhanVien_Repositoy nhanVienRepositoy;
    @Autowired
    NhanVien_Mapper nhanVienMapper;
    @Autowired
    VaiTro_Repository vaiTroRepository;
    @Autowired
    EmailSender emailSender;
    @Autowired
    NhanVienVaiTroRepository nhanVienVaiTroRepository;


    @Override
    public Page<NhanVienResponse> pageNhanVien(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.findAll(pageable);
        return nhanVienPage.map(nhanVienMapper::EntiyToResponse);
    }

    @Override
    public Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.pageSearch(pageable, search);
        return nhanVienPage.map(nhanVienMapper::EntiyToResponse);
    }

    @Override
    public List<NhanVienResponse> listNhanVienResponse() {
        return nhanVienMapper.listNhanVienEntityToNhanVienResponse(nhanVienRepositoy.getNhanVienByTrangThai(1));
    }

    @Override
    public NhanVienResponse findByEmail(String email) {
        return nhanVienMapper.EntiyToResponse(nhanVienRepositoy.findByEmail(email));
    }

    @Override
    public NhanVienResponse create(CreateNhanVien createNhanVienRequest) {
        try {
            NhanVien nhanVien = nhanVienMapper.CreateToEntity(createNhanVienRequest);
            nhanVien.setMa(GenerateCode.generateNhanVienCode());
            nhanVien.setNgayTao(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
            nhanVien.setNgayBatDauLamViec(LocalDateTime.now());
            nhanVien.setTrangThai(1);
            if (nhanVienRepositoy.findByEmail(createNhanVienRequest.getEmail()) != null) {
                throw new RuntimeException("Email đã được sử dụng trước đó.");
            }
            if (nhanVienRepositoy.findByCccd(createNhanVienRequest.getCccd()) != null) {
                throw new RuntimeException("Căn cước công dân đã tồn tại trước đó.");
            }
            if (createNhanVienRequest.getHinhAnh() != null) {
                if (createNhanVienRequest.getHinhAnh().length() > 250) {
                    throw new RuntimeException("Hình ảnh quá dài. Hãy sửa lại ảnh!");
                }
            }
            String passwordNhanVien = GenerateCode.generateNhanVienCode();

            nhanVien.setMatKhau(passwordNhanVien);
            nhanVienRepositoy.save(nhanVien);

            NhanVienVaiTro nhanVienVaiTro = new NhanVienVaiTro();
            nhanVienVaiTro.setNhanVien(nhanVien);
            VaiTro vaiTro = vaiTroRepository.findById(createNhanVienRequest.getIdVaiTro()).orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò."));
            nhanVienVaiTro.setVaiTro(vaiTro);
            nhanVienVaiTroRepository.save(nhanVienVaiTro);
            String vaiTroNhanVien = nhanVienVaiTroRepository.findNhanVienVaiTroByIdNhanVien(nhanVien.getId()).getVaiTro().getTen();
            emailSender.signupNhanVienSendEmail(nhanVien, passwordNhanVien, vaiTroNhanVien);
        } catch (Exception ex) {
            throw new RuntimeException("Tạo mới nhân viên không thành công.");
        }
        return null;
    }

    @Override
    public NhanVienResponse update(UpdateNhanVien updateNhanVienRequest, Long id) {
        try {
            // Tìm kiếm nhân viên theo ID
            Optional<NhanVien> optionalNhanVien = nhanVienRepositoy.getNhanVienById(id);
            if (optionalNhanVien.isPresent()) {
                NhanVien nhanVien = optionalNhanVien.get();

                // Mapping DTO to Entity
                NhanVien updateNhanVien = nhanVienMapper.UpdateToEntity(updateNhanVienRequest);

                // Kiểm tra email trùng lặp
                nhanVienRepositoy.findAll().stream()
                        .filter(kh -> kh.getEmail().equals(updateNhanVien.getEmail()) && !kh.getId().equals(nhanVien.getId()))
                        .findFirst()
                        .ifPresent(kh -> {
                            throw new RuntimeException("Email này đã tồn tại " + kh.getEmail());
                        });

                // Kiểm tra căn cước công dân trùng lặp
                nhanVienRepositoy.findAll().stream()
                        .filter(kh -> kh.getCccd().equals(updateNhanVien.getCccd()) && !kh.getId().equals(nhanVien.getId()))
                        .findFirst()
                        .ifPresent(kh -> {
                            throw new RuntimeException("Số căn cước công dân này đã tồn tại " + kh.getCccd());
                        });

                // Kiểm tra số điện thoại trùng lặp
                nhanVienRepositoy.findAll().stream()
                        .filter(kh -> kh.getSdt().equals(updateNhanVien.getSdt()) && !kh.getId().equals(nhanVien.getId()))
                        .findFirst()
                        .ifPresent(kh -> {
                            throw new RuntimeException("Số điện thoại này đã tồn tại " + kh.getSdt());
                        });

                // Cập nhật các trường của nhân viên
                nhanVien.setTrangThai(updateNhanVien.getTrangThai());
                nhanVien.setTen(updateNhanVien.getTen());
                nhanVien.setHinhAnh(updateNhanVien.getHinhAnh());
                nhanVien.setGioiTinh(updateNhanVien.getGioiTinh());
                nhanVien.setNgaySinh(updateNhanVien.getNgaySinh());
                nhanVien.setCccd(updateNhanVien.getCccd());
                nhanVien.setEmail(updateNhanVien.getEmail());
                nhanVien.setSdt(updateNhanVien.getSdt());
                nhanVien.setNgaySua(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

                // Lưu nhân viên
                NhanVien updatedNhanVien = nhanVienRepositoy.save(nhanVien);

                // Cập nhật vai trò của nhân viên
                if (updateNhanVienRequest.getIdVaiTro() != null) {
                    VaiTro vaiTro = vaiTroRepository.findById(updateNhanVienRequest.getIdVaiTro())
                            .orElseThrow(() -> new RuntimeException("Vai trò không tồn tại với ID: " + updateNhanVienRequest.getIdVaiTro()));

                    // Kiểm tra xem nhân viên đã có vai trò này chưa
                    boolean hasRole = nhanVien.getNhanVienVaiTros().stream()
                            .anyMatch(nvvt -> nvvt.getVaiTro().getId().equals(vaiTro.getId()));

                    if (!hasRole) {
                        NhanVienVaiTro nhanVienVaiTro = new NhanVienVaiTro();
                        nhanVienVaiTro.setNhanVien(nhanVien);
                        nhanVienVaiTro.setVaiTro(vaiTro);

                        nhanVienVaiTroRepository.save(nhanVienVaiTro);
                    }
                }
                // Trả về phản hồi
                return nhanVienMapper.EntiyToResponse(updatedNhanVien);

            } else {
                throw new RuntimeException("Nhân viên không tồn tại với ID: " + id);
            }

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to update nhân viên. Possibly duplicate record." + ex.getMessage(), ex);
        }
    }


    @Override
    public NhanVienResponse getOne(Integer id) {
        NhanVien nhanVien = nhanVienRepositoy.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
        return nhanVienMapper.EntiyToResponse(nhanVien);
    }


    @Override
    public void removeOrRevert(Long id) {
        nhanVienRepositoy.updateTrangThaiById(0, id);
    }

    @Override
    public boolean changeEmail(ChangeEmail_Dto changeEmailDto, String newEmailNv) {
        NhanVien nhanVien = nhanVienRepositoy.findByEmail(changeEmailDto.getEmail());
        if (nhanVien == null) {
            return false;
        }
        nhanVien.setEmail(newEmailNv);
        nhanVienRepositoy.save(nhanVien);
        return true;
    }

    @Override
    public void sendForgotPasswordEmailForNhanVien(String email) {
        NhanVien nhanVien = nhanVienRepositoy.findByEmail(email);
        if (nhanVien != null) {
            String newPlainTextPassword = GenerateCode.generatePassWordNhanVien();
            nhanVien.setMatKhau(newPlainTextPassword);
            nhanVienRepositoy.save(nhanVien);
            emailSender.sendForgotPasswordEmailForNhanVien(nhanVien, newPlainTextPassword);
        } else {
            throw new RuntimeException("Không tìm thấy thông tin nhân viên trong hệ thống.");
        }
    }

    @Override
    public void updatePassword(String newPassword, String email) {
        nhanVienRepositoy.updatePasswordEmployee(newPassword, email);
    }

    @Override
    public void updateImageNV(String image, String email) {
        nhanVienRepositoy.updateImageEmployee(image, email);
    }
}

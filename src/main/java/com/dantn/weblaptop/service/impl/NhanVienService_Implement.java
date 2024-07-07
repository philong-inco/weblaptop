package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.AccountStatus;
import com.dantn.weblaptop.constant.EmailSender;
import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.NhanVienVaiTro;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.exception.GlobalException;
import com.dantn.weblaptop.mapper.NhanVien_Mapper;
import com.dantn.weblaptop.mapper.VaiTro_Mapper;
import com.dantn.weblaptop.repository.NhanVienVaiTroRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import com.dantn.weblaptop.service.NhanVien_Service;
import com.dantn.weblaptop.util.ConvertTime;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.List;


@AllArgsConstructor
public class NhanVienService_Implement implements NhanVien_Service {
    private final NhanVien_Repositoy nhanVienRepositoy;
    private final NhanVien_Mapper nhanVienMapper;
    private final VaiTro_Mapper vaiTroMapper;
    private final VaiTro_Repository vaiTroRepository;
    private final EmailSender emailSender;
    private final NhanVienVaiTroRepository nhanVienVaiTroRepository;

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
        return nhanVienMapper.listNhanVienEntityToNhanVienResponse(nhanVienRepositoy.getNhanVienByTrangThai(AccountStatus.DANG_LAM_VIEC));
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
            nhanVien.setTrangThai(AccountStatus.DANG_LAM_VIEC.ordinal());
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
            emailSender.signupNhanVienSendEmail(nhanVien, passwordNhanVien);
            nhanVien.setMatKhau(passwordNhanVien);
            nhanVienRepositoy.save(nhanVien);

            NhanVienVaiTro nhanVienVaiTro = new NhanVienVaiTro();
            nhanVienVaiTro.setNhanVien(nhanVien);
            VaiTro vaiTro = vaiTroRepository.findById(createNhanVienRequest.getIdVaiTro()).orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò."));
            nhanVienVaiTro.setVaiTro(vaiTro);
            nhanVienVaiTroRepository.save(nhanVienVaiTro);

        } catch (Exception ex) {
            throw new RuntimeException("Tạo mới nhân viên không thành công.");
        }
        return null;
    }

    @Override
    public NhanVienResponse update(UpdateNhanVien updateNhanVienRequest, Integer id) {
        try {
            NhanVien nhanVien = nhanVienRepositoy.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));

            NhanVienVaiTro nhanVienVaiTro = nhanVienVaiTroRepository.findNhanVienVaiTroByIdNhanVien(nhanVien.getId());

            VaiTro vaiTro = nhanVienVaiTro.getVaiTro();
            if (vaiTro == null || vaiTro.getId() == null) {
                throw new RuntimeException("Không tìm thấy vai trò với ID: " + id);
            }

            NhanVien updateNhanVien = nhanVienMapper.UpdateToEntity(updateNhanVienRequest);

            for (NhanVien kh : nhanVienRepositoy.findAll()) {
                if (kh.getEmail().equals(updateNhanVien.getEmail()) && kh.getId() != nhanVien.getId()) {
                    throw new RuntimeException("Email này đã tồn tại " + kh.getEmail());
                }
            }

            for (NhanVien kh : nhanVienRepositoy.findAll()) {
                if (kh.getCccd().equals(updateNhanVien.getCccd()) && kh.getId() != nhanVien.getId()) {
                    throw new RuntimeException("Số căn cước công dân này đã tồn tại " + kh.getCccd());
                }
            }

            for (NhanVien kh : nhanVienRepositoy.findAll()) {
                if (kh.getSdt().equals(updateNhanVien.getSdt()) && kh.getId() != nhanVien.getId()) {
                    throw new RuntimeException("Số điện thoại này đã tồn tại " + kh.getSdt());
                }
            }
            nhanVien.setTen(updateNhanVien.getTen());
            nhanVien.setHinhAnh(updateNhanVien.getHinhAnh());
            nhanVien.setGioiTinh(updateNhanVien.getGioiTinh());
            nhanVien.setNgaySinh(updateNhanVien.getNgaySinh());
            nhanVien.setCccd(updateNhanVien.getCccd());
            nhanVien.setEmail(updateNhanVien.getEmail());
            nhanVien.setSdt(updateNhanVien.getSdt());
            nhanVien.setNgaySua(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

            return nhanVienMapper.EntiyToResponse(nhanVienRepositoy.save(nhanVien));
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to update nhan vien. Possibly duplicate record." + ex);
        }

    }


    @Override
    public NhanVienResponse getOne(Integer id) {
        NhanVien nhanVien = nhanVienRepositoy.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
        return nhanVienMapper.EntiyToResponse(nhanVien);
    }


    @Override
    public void removeOrRevert(AccountStatus accountStatus, Long id) {
        nhanVienRepositoy.revertStatus(accountStatus.DA_THOI_VIEC, id);
    }

    @Override
    public boolean changeEmail(NhanVienResponse nhanVienDto, String newEmailNv) {
        NhanVien nhanVien = nhanVienRepositoy.findByEmail(nhanVienDto.getEmail());
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

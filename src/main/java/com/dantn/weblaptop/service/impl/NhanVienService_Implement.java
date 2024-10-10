package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.EmailSender;
import com.dantn.weblaptop.dto.ChangeEmail_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.NhanVienVaiTro;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.mapper.NhanVien_Mapper;
import com.dantn.weblaptop.mapper.VaiTro_Mapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    VaiTro_Mapper vaiTroMapper;
    @Autowired
    NhanVienVaiTroRepository nhanVienVaiTroRepository;
    @Autowired
    private NhanVien_Repositoy nhanVien_Repositoy;


    @Override
    public Page<NhanVienResponse> pageNhanVien(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.findAll(pageable);

        return nhanVienPage.map(nhanVien -> {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));

            // Remove the last comma and space if vaiTrosBuilder is not empty
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            return response;
        });
    }


    @Override
    public Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.pageSearch(pageable, search);
        return nhanVienPage.map(nhanVien -> {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));

            // Remove the last comma and space if vaiTrosBuilder is not empty
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            return response;
        });
    }

    @Override
    public Page<NhanVienResponse> pageSearchTrangThaiNhanVien(Integer pageNo, Integer size, Integer trangThai) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.getNhanVienByTrangThai(pageable, trangThai);

        return nhanVienPage.map(nhanVien -> {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            return response;
        });
    }


    @Override
    public NhanVienResponse findByEmail(String email) {
        return nhanVienMapper.EntiyToResponse(nhanVienRepositoy.findByEmail(email));
    }

    @Override
    public NhanVienResponse create(CreateNhanVien createNhanVienRequest) {
        try {
            if (nhanVienRepositoy.findByEmail(createNhanVienRequest.getEmail()) != null) {
                throw new RuntimeException("Email đã được sử dụng trước đó.");
            }
            NhanVien nhanVien = prepareNhanVienEntity(createNhanVienRequest);
            validateNhanVien(createNhanVienRequest);
            nhanVien = saveNhanVien(nhanVien);

            assignRolesToNhanVien(nhanVien, createNhanVienRequest.getListVaiTro());

            Set<VaiTro> vaiTros = vaiTroRepository.findVaiTroByTen(createNhanVienRequest.getListVaiTro());

            sendSignupEmailAsync(nhanVien, vaiTros);

            return mapToResponse(nhanVien);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new RuntimeException("Tạo mới nhân viên không thành công.");
        }
    }

    private NhanVien prepareNhanVienEntity(CreateNhanVien createNhanVienRequest) {
        NhanVien nhanVien = nhanVienMapper.CreateToEntity(createNhanVienRequest);
        nhanVien.setMa(GenerateCode.generateNhanVienCode());
        nhanVien.setNgayTao(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        nhanVien.setNgayBatDauLamViec(LocalDateTime.now());
        nhanVien.setTrangThai(1);
        nhanVien.setMatKhau(GenerateCode.generateNhanVienCode());
        return nhanVien;
    }

    private void validateNhanVien(CreateNhanVien createNhanVienRequest) {
        if (nhanVienRepositoy.findByEmail(createNhanVienRequest.getEmail()) != null) {
            throw new RuntimeException("Email đã được sử dụng trước đó.");
        }
        if (createNhanVienRequest.getHinhAnh() != null && createNhanVienRequest.getHinhAnh().length() > 250) {
            throw new RuntimeException("Hình ảnh quá dài. Hãy sửa lại ảnh!");
        }
    }

    private NhanVien saveNhanVien(NhanVien nhanVien) {
        return nhanVienRepositoy.save(nhanVien);
    }

    private void sendSignupEmailAsync(NhanVien nhanVien, Set<VaiTro> vaiTros) {
        new Thread(() -> {
            try {
                emailSender.newEmployeeSendEmail(nhanVien, nhanVien.getMatKhau(), vaiTros);
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Xảy ra lỗi khi gửi email.");
            }
        }).start();
    }

    private NhanVienResponse mapToResponse(NhanVien nhanVien) {
        // Mapping logic từ NhanVien sang NhanVienResponse
        return nhanVienMapper.EntiyToResponse(nhanVien);
    }

    private void assignRolesToNhanVien(NhanVien nhanVien, Set<String> vaiTroStrings) {
        Set<VaiTro> vaiTros = vaiTroRepository.findVaiTroByTen(vaiTroStrings);
        vaiTros.forEach(vt -> {
            nhanVienVaiTroRepository.save(new NhanVienVaiTro(nhanVien, vt));
        });
    }

    @Override
    public NhanVienResponse update(UpdateNhanVien updateNhanVienRequest, Long id) {
        try {
            // Tìm kiếm nhân viên theo ID
            Optional<NhanVien> optionalNhanVien = nhanVienRepositoy.findById(id);
            if (optionalNhanVien.isPresent()) {
                NhanVien nhanVien = optionalNhanVien.get();

                // Mapping DTO to Entity
                NhanVien updateNhanVien = nhanVienMapper.UpdateToEntity(updateNhanVienRequest);

                // Kiểm tra email trùng lặp
                nhanVienRepositoy.findAll().stream().filter(nv -> nv.getEmail().equals(updateNhanVien.getEmail()) && !nv.getId().equals(nhanVien.getId())).findFirst().ifPresent(nv -> {
                    throw new RuntimeException("Email này đã tồn tại: " + nv.getEmail());
                });

                // Kiểm tra số điện thoại trùng lặp
                nhanVienRepositoy.findAll().stream().filter(nv -> nv.getSdt().equals(updateNhanVien.getSdt()) && !nv.getId().equals(nhanVien.getId())).findFirst().ifPresent(nv -> {
                    throw new RuntimeException("Số điện thoại này đã tồn tại: " + nv.getSdt());
                });

                // Cập nhật các trường của nhân viên
                nhanVien.setTen(updateNhanVien.getTen());
                nhanVien.setHinhAnh(updateNhanVien.getHinhAnh());
                nhanVien.setGioiTinh(updateNhanVien.getGioiTinh());
                nhanVien.setNgaySinh(updateNhanVien.getNgaySinh());
                nhanVien.setEmail(updateNhanVien.getEmail());
                nhanVien.setSdt(updateNhanVien.getSdt());
                nhanVien.setDiaChi(updateNhanVien.getDiaChi());
                nhanVien.setNgaySua(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

                // Xóa hết vai trò hiện tại của nhân viên
                nhanVienVaiTroRepository.deleteByNhanVien(id);


//                Set<VaiTro> vaiTros = vaiTroRepository.findVaiTroByTen(updateNhanVienRequest.getListVaiTro());
                // Lưu nhân viên
                NhanVien updatedNhanVien = nhanVienRepositoy.save(nhanVien);

                assignRolesToNhanVien(updatedNhanVien, updateNhanVienRequest.getListVaiTro());
                // Trả về phản hồi
                return nhanVienMapper.EntiyToResponse(updatedNhanVien);
            } else {
                throw new RuntimeException("Nhân viên không tồn tại với ID: " + id);
            }

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to update nhân viên. Possibly duplicate record: " + ex.getMessage(), ex);
        }
    }

    @Override
    public NhanVienResponse getOne(Long id) {
        NhanVien nhanVien = nhanVienRepositoy.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
        return nhanVienMapper.EntiyToResponse(nhanVien);
    }

    @Override
    public List<VaiTro_Response> findVaiTroByNhanVien(Long id) {
        List<VaiTro> vaiTroList = nhanVienVaiTroRepository.findByIdNhanVien(id);
        return vaiTroMapper.listVaiTroEntityToVaiTroResponse(vaiTroList);
    }

    @Override
    public List<NhanVienResponse> getDanhSachNhanVien() {
        List<NhanVien> danhSachNhanVien = nhanVien_Repositoy.getDanhSachNhanVien();
        List<NhanVienResponse> danhSachNhanVienResponse = new ArrayList<>();

        for (NhanVien nhanVien : danhSachNhanVien) {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));

            // Remove the last comma and space if vaiTrosBuilder is not empty
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            danhSachNhanVienResponse.add(response);
        }

        return danhSachNhanVienResponse;
    }


    @Override
    public void removeOrRevert(Long id) {
        nhanVienRepositoy.updateTrangThaiById(0, id);
    }

    @Override
    public void rollBackStatusNhanVien(Long id) {
        nhanVienRepositoy.updateTrangThaiById(1, id);
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
            emailSender.sendForgotPasswordEmail(nhanVien, newPlainTextPassword);
        } else {
            throw new RuntimeException("Không tìm thấy thông tin nhân viên trong hệ thống.");
        }
    }

    @Override
    public void updatePassword(String newPassword, String email) {
        nhanVienRepositoy.updatePasswordEmployee(newPassword, email);
    }

    @Override
    public void updateImageNV(String image, Long id) {
        nhanVienRepositoy.updateImageEmployee(image, id);
    }

    @Override
    public Page<NhanVienResponse> searchNhanVienByGioiTinh(Integer pageNo, Integer size, Integer gioiTinh) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.getNhanVienByGioiTinh(pageable, gioiTinh);
        return nhanVienPage.map(nhanVien -> {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));

            // Remove the last comma and space if vaiTrosBuilder is not empty
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            return response;
        });
    }

    @Override
    public Page<NhanVienResponse> searchNhanVienByNamSinh(Integer pageNo, Integer size, Integer year) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepositoy.getNhanVienByNamSinh(pageable, year);
        return nhanVienPage.map(nhanVien -> {
            StringBuilder vaiTrosBuilder = new StringBuilder();
            nhanVienVaiTroRepository.findByNhanVien(nhanVien).forEach(vaiTro -> vaiTrosBuilder.append(vaiTro.getTen()).append(", "));

            // Remove the last comma and space if vaiTrosBuilder is not empty
            if (vaiTrosBuilder.length() > 0) {
                vaiTrosBuilder.setLength(vaiTrosBuilder.length() - 2);
            }

            NhanVienResponse response = nhanVienMapper.EntiyToResponse(nhanVien);
            response.setVaiTro(vaiTrosBuilder.toString());
            return response;
        });
    }
}

package com.dantn.weblaptop.service;

import com.dantn.weblaptop.constant.AccountStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateNhanVien;
import com.dantn.weblaptop.dto.request.update_request.UpdateNhanVien;
import com.dantn.weblaptop.dto.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface NhanVien_Service {
    Page<NhanVienResponse> pageNhanVien(Integer pageNo, Integer size);

    Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo, Integer size, String search);

    List<NhanVienResponse> listNhanVienResponse();

    NhanVienResponse findByEmail(String email);

    NhanVienResponse create(CreateNhanVien createNhanVienRequest);

    NhanVienResponse update(UpdateNhanVien updateNhanVienRequest, Integer id);

    NhanVienResponse getOne(Integer id);

    void removeOrRevert(AccountStatus accountStatus, Long id);

    boolean changeEmail(NhanVienResponse nhanVienDto, String newEmailNv);

    void sendForgotPasswordEmailForNhanVien(String email);

    void updatePassword(String newPassword, String email);

    void updateImageNV(String image, String email);
}

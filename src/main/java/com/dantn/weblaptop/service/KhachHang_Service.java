package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.InfomationKhachHang;
import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("khachHang_Service")
public interface KhachHang_Service {
    Page<KhachHangResponse> pageKhachHang(Integer pageNo, Integer size);

    Page<KhachHangResponse> pageSearchKhachHang(Integer pageNo, Integer size, String search);

    List<KhachHangResponse> listKhachHangResponse();

    List<InfomationKhachHang> listKhachHangInfo();

    KhachHangResponse create(CreateKhachHang createKhachHangRequest, HttpServletRequest request);

    KhachHangResponse findKhachHangByEmail(String email);

    boolean checkMail(String email);

    KhachHangResponse update(UpdateKhachHang updateKhachHangRequest, Long id);

    KhachHangResponse getOne(Long id);

    void removeOrRevert(Integer trangThaiKhachHang, Long id);

    boolean suaThongTin(InfomationKhachHang infomationKhachHang, Long id);

    boolean changeEmail(InfomationKhachHang infomationKhachHang, String newEmail);

    void updateImage(String image, String email);
}

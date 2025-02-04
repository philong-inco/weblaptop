package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.InfomationKhachHang;
import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.exception.AppException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service("khachHang_Service")
public interface KhachHang_Service {
    Page<KhachHangResponse> pageKhachHang(Integer pageNo, Integer size);

    Page<KhachHangResponse> pageSearchHang (Integer pageNo, Integer size, Integer hangKhachHang);

    Page<KhachHangResponse> pageSearchGioiTinh(Integer pageNo, Integer size, Integer gioiTinh);

    Page<KhachHangResponse> pageSearchKhachHang(Integer pageNo, Integer size, String search);

    List<KhachHangResponse> listKhachHangResponse();

    List<KhachHangResponse> listKhachHang();

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

    Long countKhachHangByDate(Long startDate, Long endDate);

    KhachHangResponse findCustomerByPhone(String phone) throws AppException;

    KhachHang updateForgotPassword(Long id, String newPassword);

    KhachHangResponse login(String email, String password);

    void sentEmailForgotPassword(String email) throws MessagingException;
}

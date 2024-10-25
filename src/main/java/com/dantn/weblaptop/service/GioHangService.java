package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.AddToGioHangRequest;
import com.dantn.weblaptop.dto.request.create_request.GioHangRequest;
import com.dantn.weblaptop.dto.response.CartResponse;
import com.dantn.weblaptop.dto.response.GioHangDetailResponse;
import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface GioHangService {

    void createGioHang(KhachHang khachHang);

    CartResponse addToCart(AddToGioHangRequest request, HttpServletRequest httpServletRequest) throws AppException;

    List<GioHangDetailResponse> getListCart(String sessionId , Long idKhachHang) throws AppException;

    Integer quantityInCart(String sessionId , Long idKhachHang) throws AppException;

}

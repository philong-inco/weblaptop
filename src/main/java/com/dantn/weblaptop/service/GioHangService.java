package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.AddToGioHangRequest;
import com.dantn.weblaptop.dto.response.GioHangDetailResponse;
import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.exception.AppException;

import java.util.List;

public interface GioHangService {

    void createGioHang(KhachHang khachHang);

    GioHang addToCart(AddToGioHangRequest request) throws AppException;

    List<GioHangDetailResponse> getListCart(Long idKhachHang) throws AppException;

    Integer quantityInCart(Long idKhachHang);

}

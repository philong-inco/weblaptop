package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;

import java.util.List;

public interface KhachHang_Mapper {

    KhachHang createToEntityKhachHang(CreateKhachHang createKhachHang);

    KhachHang updateToEntityKhachHang(UpdateKhachHang updateKhachHang);

    KhachHangResponse entityToResponseKhachHang(KhachHang khachHang);

    List<KhachHangResponse> listKhachHangToKhachHangResponse(List<KhachHang> khachHangList);
}

package com.dantn.weblaptop.mapper;

import com.dantn.weblaptop.dto.request.create_request.CreateKhachHang;
import com.dantn.weblaptop.dto.request.update_request.UpdateKhachHang;
import com.dantn.weblaptop.dto.response.KhachHangResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface KhachHang_Mapper {
    KhachHang createToEntity(CreateKhachHang createKhachHang);

    KhachHang updateToEntity(UpdateKhachHang updateKhachHang);

    KhachHangResponse entityToResponse(KhachHang khachHang);

    List<KhachHangResponse> listKhachHangToKhachHangResponse(List<KhachHang> khachHangList);
}

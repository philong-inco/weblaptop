package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;

public class SerialNumberSoldMapper {

    public static SerialNumberDaBanResponse toSerialNumberDaBanResponse(SerialNumberDaBan serialNumberSold) {
        SerialNumberDaBanResponse response = new SerialNumberDaBanResponse();
        response.setBillId(serialNumberSold.getHoaDon().getId());
        response.setProductDetailId(serialNumberSold.getSerialNumber().getSanPhamChiTiet().getId());
        response.setProductDetailCode(serialNumberSold.getSerialNumber().getSanPhamChiTiet().getMa());
        String name =serialNumberSold.getSerialNumber().getSanPhamChiTiet().getSanPham().getTen();
        String ram =serialNumberSold.getSerialNumber().getSanPhamChiTiet().getRam().getTen();
        String oCung = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getOCung().getTen();
        String color = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getMauSac().getTen();
        response.setProductName(name + " " + color + " [ " + ram + " - " + oCung + " ]");
        response.setPrice(serialNumberSold.getGiaBan());
        return response;
    }
}

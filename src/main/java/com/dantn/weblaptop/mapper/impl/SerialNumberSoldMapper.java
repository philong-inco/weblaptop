package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;

import java.util.Set;

public class SerialNumberSoldMapper {

    public static SerialNumberDaBanResponse toSerialNumberDaBanResponse(SerialNumberDaBan serialNumberSold) {
        SerialNumberDaBanResponse response = new SerialNumberDaBanResponse();
        response.setBillId(serialNumberSold.getHoaDon().getId());
        response.setProductDetailId(serialNumberSold.getSerialNumber().getSanPhamChiTiet().getId());
        response.setProductDetailCode(serialNumberSold.getSerialNumber().getSanPhamChiTiet().getMa());
        String name =serialNumberSold.getSerialNumber().getSanPhamChiTiet().getSanPham().getTen();
        String ram =serialNumberSold.getSerialNumber().getSanPhamChiTiet().getRam().getTen();
        String core = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getCpu().getTen();
        String oCung = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getOCung().getTen();
        String color = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getMauSac().getTen();
        response.setProductName(name +  " [ " + ram + " - "+ core + "-" + oCung + "-"+color +" ]");
        response.setPrice(serialNumberSold.getGiaBan());
        Set<AnhSanPham> anhSanPhams = serialNumberSold.getSerialNumber().getSanPhamChiTiet().getAnhSanPhams();
        if (anhSanPhams != null && !anhSanPhams.isEmpty()) {
            AnhSanPham anhDauTien = anhSanPhams.stream().findFirst().orElse(null);
            if (anhDauTien != null) {
                response.setAnh(anhDauTien.getUrl());
            }
        }        return response;
    }
}

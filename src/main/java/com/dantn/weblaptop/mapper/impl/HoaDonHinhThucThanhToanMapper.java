package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.pdf.HoaDonHinhThucThanhToanPdfReponse;
import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
import com.dantn.weblaptop.util.BillUtils;

public class HoaDonHinhThucThanhToanMapper {

    public static HoaDonHinhThucThanhToanPdfReponse toHoaDonHinhThucThanhToanPdfReponse(HoaDonHinhThucThanhToan hinhThucThanhToan){
        HoaDonHinhThucThanhToanPdfReponse pdfReponse = new HoaDonHinhThucThanhToanPdfReponse();
        pdfReponse.setMaGioDich(hinhThucThanhToan.getMaGioDich());
        pdfReponse.setSoTien(BillUtils.convertMoney(hinhThucThanhToan.getSoTien()));
        pdfReponse.setTienNhan(BillUtils.convertMoney(hinhThucThanhToan.getTienNhan()));
        pdfReponse.setTrangThai(hinhThucThanhToan.getTrangThai());
        pdfReponse.setLoaiThanhToan(hinhThucThanhToan.getLoaiThanhToan());
        return pdfReponse;
    }
}

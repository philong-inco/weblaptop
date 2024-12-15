package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.pdf.BillPdfResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.BillUtils;
import com.dantn.weblaptop.util.ConvertTime;
import com.dantn.weblaptop.util.QrCode;
import com.google.zxing.WriterException;
import org.hibernate.annotations.Comment;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Comment("hoaDonMapper")
public class HoaDonMapper {

    public static HoaDonResponse   toHoaDonResponse (HoaDon hoaDon){
        HoaDonResponse response = new HoaDonResponse();
        KhachHang customer = hoaDon.getKhachHang();
        response.setId(hoaDon.getId());
        response.setMa(hoaDon.getMa());
        if(hoaDon.getNhanVien() !=null){
            response.setIdNhanVien(hoaDon.getNhanVien().getId());
            response.setTenNhanVien(hoaDon.getNhanVien().getTen());
            response.setMaNhanVien(hoaDon.getNhanVien().getMa());
        }
        response.setTenKhachHang(customer != null ? customer.getTen() : hoaDon.getTenKhachHang());
        response.setIdKhachHang(
                customer !=null ? customer.getId() : null);
        response.setGhiChu(hoaDon.getGhiChu());
        response.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
        response.setTongTienPhaiTra(hoaDon.getTongTienPhaiTra());
        response.setLoaiHoaDon(hoaDon.getLoaiHoaDon());
        response.setTongTienPhaiTra(hoaDon.getTongTienPhaiTra());
        response.setTongTienBanDau(hoaDon.getTongTienBanDau());
        response.setSdt(hoaDon.getSdt());
        response.setEmail(hoaDon.getEmail() );
        response.setDiaChi(hoaDon.getDiaChi());
        response.setNguoiSua(hoaDon.getNguoiSua());
        response.setNguoiTao(hoaDon.getNguoiTao());
        response.setNgayTao(hoaDon.getNgayTao() !=null ? ConvertTime.convert(hoaDon.getNgayTao()+""): null);
        response.setTrangThai(hoaDon.getTrangThai());
        response.setTongSanPham(hoaDon.getTongSanPham());

        response.setTienShip(hoaDon.getTienShip());
        response.setTienGiamHangKhachHang(hoaDon.getTienGiamHangKhachHang());
        response.setThanhToanSau(hoaDon.getThanhToanSau());
        response.setNgayGiaoHang(hoaDon.getNgayGiaoHang());
        response.setNgayNhanHang(hoaDon.getNgayNhanHang());
        response.setNgayThanhToan(hoaDon.getNgayThanhToan());

        PhieuGiamGia phieuGiamGia = hoaDon.getPhieuGiamGia();
        if(phieuGiamGia!=null){
            response.setIdPhieuGiamGia(phieuGiamGia.getId());
            response.setGiaTriPhieuGiamGia(calculateDiscount(hoaDon, phieuGiamGia));
            response.setLoaiPGG(phieuGiamGia.getLoaiGiamGia());
            response.setMaPGG(phieuGiamGia.getMa());
        }
        if (response.getDiaChi() != null) {
            String[] diaChiParts = response.getDiaChi().split("\\|");
            for (int i = 0; i < diaChiParts.length; i++) {
                diaChiParts[i] = diaChiParts[i].trim();
            }
            if (diaChiParts.length == 4) {
                String diaChiChiTiet = diaChiParts[0];
                String phuongXa = diaChiParts[1];
                String quanHuyen = diaChiParts[2];
                String tinhThanh = diaChiParts[3];
                response.setDiaChi(diaChiChiTiet);
                response.setPhuong(phuongXa);
                response.setHuyen(quanHuyen);
                response.setTinh(tinhThanh);
            } else {
                System.out.println("Chuỗi địa chỉ không có đủ 4 phần.");
            }
        }
        return response;
    }

    private static BigDecimal calculateDiscount(HoaDon existingBill, PhieuGiamGia coupon) {
        BigDecimal moneyReduced = BigDecimal.ZERO;
        // 1 % : 2 VND
        if (coupon.getLoaiGiamGia() == 2) {
            moneyReduced = coupon.getGiaTriGiamGia();
        } else {
            // Tính % của phiếu giảm rồi trừ đi
            moneyReduced = coupon.getGiamToiDa()
                    .multiply(coupon.getGiaTriGiamGia())
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        }

        if (coupon.getGiamToiDa().compareTo(moneyReduced) < 0) {
            moneyReduced = coupon.getGiamToiDa();
        }
        return moneyReduced;
    }


    public  static  void toUpdateHoaDon (HoaDon hoaDon){

    }
    public static List<HoaDonResponse> toHoaDonResponseList(List<HoaDon> hoaDonList) {
        return hoaDonList.stream()
                .map(HoaDonMapper::toHoaDonResponse)
                .collect(Collectors.toList());
    }

    public static BillPdfResponse toBillPdfResponse(HoaDonResponse hoaDonResponse) throws IOException, WriterException {
        BillPdfResponse billPdfResponse = new BillPdfResponse();
        billPdfResponse.setId(hoaDonResponse.getId());
        billPdfResponse.setIdNhanVien(hoaDonResponse.getIdNhanVien());
        billPdfResponse.setIdKhachHang(hoaDonResponse.getIdKhachHang());
        billPdfResponse.setTenKhachHang(hoaDonResponse.getTenKhachHang());
        billPdfResponse.setMa(hoaDonResponse.getMa());
        billPdfResponse.setTongSanPham(hoaDonResponse.getTongSanPham());
        billPdfResponse.setLoaiHoaDon(hoaDonResponse.getLoaiHoaDon());
        billPdfResponse.setTongTienBanDau(BillUtils.convertMoney(hoaDonResponse.getTongTienBanDau()));
        billPdfResponse.setTongTienPhaiTra(BillUtils.convertMoney(hoaDonResponse.getTongTienPhaiTra().add(hoaDonResponse.getTienShip())));
        billPdfResponse.setSdt(hoaDonResponse.getSdt());
        billPdfResponse.setEmail(hoaDonResponse.getEmail());
        billPdfResponse.setDiaChi(hoaDonResponse.getDiaChi());
        billPdfResponse.setTienShip(BillUtils.convertMoney(hoaDonResponse.getTienShip()));
        billPdfResponse.setThanhToanSau(hoaDonResponse.getThanhToanSau());
        billPdfResponse.setNgayGiaoHang(hoaDonResponse.getNgayGiaoHang());
        billPdfResponse.setNgayNhanHang(hoaDonResponse.getNgayNhanHang());
        billPdfResponse.setNgayThanhToan(hoaDonResponse.getNgayThanhToan());
        billPdfResponse.setGhiChu(hoaDonResponse.getGhiChu());
        billPdfResponse.setTrangThai(hoaDonResponse.getTrangThai());
        billPdfResponse.setNguoiTao(hoaDonResponse.getNguoiTao());
        billPdfResponse.setNguoiSua(billPdfResponse.getNguoiSua());
        billPdfResponse.setNgayTao(hoaDonResponse.getNgayTao());
        billPdfResponse.setIdPhieuGiamGia(hoaDonResponse.getIdPhieuGiamGia());
        billPdfResponse.setMaPGG(hoaDonResponse.getMaPGG());
        billPdfResponse.setLoaiPGG(hoaDonResponse.getLoaiPGG());
        billPdfResponse.setGiaTriPhieuGiamGia(BillUtils.convertMoney(hoaDonResponse.getGiaTriPhieuGiamGia()));
        billPdfResponse.setQrCode(QrCode.generateQRCodeBase64(hoaDonResponse.getMa()));
        if (hoaDonResponse.getIdNhanVien() != null) {
            billPdfResponse.setIdNhanVien(hoaDonResponse.getIdNhanVien());
            billPdfResponse.setTenNhanVien(hoaDonResponse.getTenNhanVien());
            billPdfResponse.setMaNhanVien(hoaDonResponse.getMaNhanVien());
        } else {
            billPdfResponse.setIdNhanVien(null);
            billPdfResponse.setTenNhanVien(null);
            billPdfResponse.setMaNhanVien(null);
        }
        return billPdfResponse;
    }
}

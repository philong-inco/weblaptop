package com.dantn.weblaptop.mapper.impl;

import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.util.ConvertTime;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
@Comment("hoaDonMapper")
public class HoaDonMapper {

    public static HoaDonResponse   toHoaDonResponse (HoaDon hoaDon){
        HoaDonResponse response = new HoaDonResponse();
        KhachHang customer = hoaDon.getKhachHang();
        response.setId(hoaDon.getId());
        response.setMa(hoaDon.getMa());
        response.setIdNhanVien(hoaDon.getNhanVien().getId());
        response.setTenKhachHang(
                hoaDon.getTenKhachHang() != null ?
                        hoaDon.getTenKhachHang() :
                        (customer != null ? customer.getTen() : null)
        );
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
        response.setNgayTao(ConvertTime.convert(hoaDon.getNgayTao()+""));
        response.setTrangThai(hoaDon.getTrangThai());

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
        return response;
    }

    private static BigDecimal calculateDiscount(HoaDon existingBill, PhieuGiamGia coupon) {
        BigDecimal moneyReduced = BigDecimal.ZERO;
        // 1 % : 2 VND
        if (coupon.getLoaiGiamGia() == 2) {
            moneyReduced = coupon.getGiaTriGiamGia();
        } else {
            // Tính % của phiếu giảm rồi trừ đi
            moneyReduced = existingBill.getTongTienBanDau()
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
}

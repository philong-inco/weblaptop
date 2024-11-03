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
import java.util.List;
import java.util.stream.Collectors;

@Comment("hoaDonMapper")
public class HoaDonMapper {

    public static HoaDonResponse   toHoaDonResponse (HoaDon hoaDon){
        HoaDonResponse response = new HoaDonResponse();
        KhachHang customer = hoaDon.getKhachHang();
        response.setId(hoaDon.getId());
        response.setMa(hoaDon.getMa());
        response.setIdNhanVien(hoaDon.getNhanVien() != null ?
                        hoaDon.getNhanVien().getId() : null
                );
//        response.setTenKhachHang(
//                hoaDon.getTenKhachHang() != null ?
//                        hoaDon.getTenKhachHang() :
//                        (customer != null ? customer.getTen() : null)
//        );
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
}

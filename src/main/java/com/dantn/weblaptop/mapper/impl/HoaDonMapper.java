package com.dantn.weblaptop.hoadon.mapper;

import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.hoadon.dto.request.HoaDonRequest;
import com.dantn.weblaptop.hoadon.dto.response.HoaDonResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HoaDonMapper {

    public static HoaDonResponse toBillResponse(HoaDon bill) {
        String tinhThanh = "", quanHuyen = "", phuongXa = "", diaChi = "";
        cutAddress(bill.getDiaChi(), tinhThanh, quanHuyen, phuongXa, diaChi);

        HoaDonResponse billResponse = new HoaDonResponse();
        billResponse.setId(bill.getId());

        billResponse.setIdNhanVien(bill.getNhanVien() != null ? bill.getNhanVien().getId() : null);
        billResponse.setIdKhachHang(bill.getKhachHang() != null ? bill.getKhachHang().getId() : null);
        // sau chỉnh lại % vs tiền
        billResponse.setGiaTriPhieuGiamGia(bill.getPhieuGiamGia() != null ? bill.getPhieuGiamGia().getGiaTriGiamGia() : null);

        billResponse.setMa(bill.getMa());
        billResponse.setLoaiHoaDon(bill.getLoaiHoaDon());
        billResponse.setTongTienBanDau(bill.getTongTienBanDau());
        billResponse.setTongTienPhaiTra(bill.getTongTienPhaiTra());
        billResponse.setSdt(bill.getSdt());
        billResponse.setEmail(bill.getEmail());
        billResponse.setTinhThanh(tinhThanh);
        billResponse.setQuanHuyen(quanHuyen);
        billResponse.setXaPhuong(phuongXa);
        billResponse.setDiaChi(diaChi);
        billResponse.setGhiChu(bill.getGhiChu());
        billResponse.setTrangThai(bill.getTrangThai());
        return billResponse;
    }

    public static HoaDon createBill(HoaDonRequest request) {
        // ma , NV , KH , maPGG , tongTienPhaiTra , ngayMongMuonNhanHang -> service
        StringBuilder address = new StringBuilder();
        address.append(request.getTinhThanh() + " , ");
        address.append(request.getQuanHuyen() + " , ");
        address.append(request.getXaPhuong() + " , ");
        address.append(request.getDiaChi());

        log.info("Address : " + address);
        HoaDon newBill = new HoaDon();
        newBill.setLoaiHoaDon(request.getLoaiHoaDon());
        newBill.setTongTienBanDau(request.getTongTienBanDau());
        newBill.setSdt(request.getSdt());
        newBill.setEmail(request.getEmail());
        newBill.setDiaChi(address.toString());
        newBill.setGhiChu(request.getGhiChu());
        return newBill;
    }

    public static void cutAddress(String fullAddress, String tinhThanh, String quanHuyen, String phuongXa, String diaChi) {
        String[] parts = fullAddress.split("\\s*,\\s*");
        if (parts.length >= 3) {
            tinhThanh = parts[0];
            quanHuyen = parts[1];
            phuongXa = parts[2];

            StringBuilder diaChiBuilder = new StringBuilder();
            for (int i = 3; i < parts.length; i++) {
                if (i > 3) {
                    diaChiBuilder.append(", ");
                }
                diaChiBuilder.append(parts[i]);
            }
            diaChi = diaChiBuilder.toString();

            log.info("tinhThanh: " + tinhThanh);
            log.info("quanHuyen: " + quanHuyen);
            log.info("phuongXa: " + phuongXa);
            log.info("diaChi: " + diaChi);
        } else {
            log.error("Chuỗi không hợp lệ. Vui lòng kiểm tra lại.");
        }
    }
}

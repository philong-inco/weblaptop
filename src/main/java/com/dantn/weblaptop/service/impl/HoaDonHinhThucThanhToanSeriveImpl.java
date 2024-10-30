package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HoaDonHinhThucThanhToanResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.repository.HinhThucThanhToanRepository;
import com.dantn.weblaptop.repository.HoaDonHinhThucThanhToanRepository;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.service.HoaDonHinhThucThanhToanSerive;
import com.dantn.weblaptop.util.ConvertTime;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonHinhThucThanhToanSeriveImpl implements HoaDonHinhThucThanhToanSerive {

    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    HinhThucThanhToanRepository hinhThucThanhToanRepository;
    HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDonHinhThucThanhToanResponse> getAllByBillCode(String billCode) {
        List<HoaDonHinhThucThanhToan> result = hoaDonHinhThucThanhToanRepository.findAllByHoaDonMa(billCode);
        return result.stream().map(hoaDonHinhThucThanhToan ->
        {
            return HoaDonHinhThucThanhToanResponse.builder()
                    .id(hoaDonHinhThucThanhToan.getId())
                    .soTien(hoaDonHinhThucThanhToan.getSoTien())
                    .loaiThanhToan(hoaDonHinhThucThanhToan.getLoaiThanhToan())
                    .phuongThanhToan(hoaDonHinhThucThanhToan.getHinhThucThanhToan() != null ? hoaDonHinhThucThanhToan.getHinhThucThanhToan().getId() : null)
                    .tienNhan(hoaDonHinhThucThanhToan.getTienNhan())
//                    .maGiaoDich("")
                    .ngayTao(ConvertTime.convert(hoaDonHinhThucThanhToan.getNgayTao() + ""))
                    .ngaySua(ConvertTime.convert(hoaDonHinhThucThanhToan.getNgaySua() + ""))
                    .nguoiTao(hoaDonHinhThucThanhToan.getNguoiTao())
                    .build();
        }).toList();
    }

    @Override
    public HoaDonHinhThucThanhToanResponse create(CreateHoaDonHinhThucThanhToanRequest request, String billCode) throws AppException {
        HinhThucThanhToan payment = hinhThucThanhToanRepository.findById(request.getIdHTTT()).orElseThrow(
                () -> new AppException(ErrorCode.PAY_NO_FOUND));
        HoaDon bill = hoaDonRepository.findHoaDonByMa(billCode).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        HoaDonHinhThucThanhToan paymentHistory = new HoaDonHinhThucThanhToan();
        paymentHistory.setSoTien(request.getSoTien());
        paymentHistory.setTienNhan(request.getTienNhan());
        paymentHistory.setHoaDon(bill);
        paymentHistory.setNguoiTao("Nguyễn Tiến Mạnh");
        paymentHistory.setNguoiSua("Nguyễn Tiến Mạnh");
        paymentHistory.setLoaiThanhToan(request.getLoaiThanhToan());
        paymentHistory.setHinhThucThanhToan(payment);
        bill.setEmail(request.getEmail());
        bill.setSdt(request.getSdt());
        bill.setTenKhachHang(request.getTen());
        if (request.getLoaiThanhToan() == 0) {
        } else {
            // suyx nghix theem
            System.out.println("Crete trả sau");
        }


        List<HoaDonHinhThucThanhToan> listHDHTT = hoaDonHinhThucThanhToanRepository.findAllByHoaDonIdAndLoaiThanhToan(bill.getId(), 0);
        for (HoaDonHinhThucThanhToan hoaDonHinhThucThanhToan : listHDHTT) {
            // So sánh hoaDonHinhThucThanhToan.getSoTien() với request.getSoTien()
            if (hoaDonHinhThucThanhToan.getSoTien().compareTo(request.getSoTien()) > 0) {
                // Nếu hoaDonHinhThucThanhToan.getSoTien() lớn hơn request.getSoTien(), set lại giá trị
                hoaDonHinhThucThanhToan.setSoTien(request.getSoTien());
            } else if (hoaDonHinhThucThanhToan.getSoTien().compareTo(request.getSoTien()) < 0) {
                // Nếu nhỏ hơn, trừ đi phần chênh lệch
                BigDecimal difference = request.getSoTien().subtract(hoaDonHinhThucThanhToan.getSoTien());
                hoaDonHinhThucThanhToan.setSoTien(hoaDonHinhThucThanhToan.getSoTien().subtract(difference));
            }

            // Lưu lại sau khi cập nhật giá trị
            hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan);
        }
        hoaDonHinhThucThanhToanRepository.save(paymentHistory);
//        bill.setLoaiHoaDon(request.getLoaiHoaDon());
//        bill.setTienShip(request.getTienShip());
        hoaDonRepository.save(bill);
        return null;
    }

    @Override
    public void delete(Long id) throws AppException {
        hoaDonHinhThucThanhToanRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PAYMENT_HISTORY_NOT_FOUND)
        );
        hoaDonHinhThucThanhToanRepository.deleteById(id);
    }


}

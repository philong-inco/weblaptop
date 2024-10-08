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
        List<HoaDonHinhThucThanhToan> result = hoaDonHinhThucThanhToanRepository.findByHoaDonMa(billCode);
        return result.stream().map(hoaDonHinhThucThanhToan ->
        {
            return HoaDonHinhThucThanhToanResponse.builder()
                    .id(hoaDonHinhThucThanhToan.getId())
                    .soTien(hoaDonHinhThucThanhToan.getSoTien())
                    .loaiThanhToan(hoaDonHinhThucThanhToan.getLoaiThanhToan())
                    .phuongThanhToan(hoaDonHinhThucThanhToan.getHinhThucThanhToan().getId())
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
        paymentHistory.setHoaDon(bill);
        paymentHistory.setLoaiThanhToan(request.getLoaiThanhToan());
        paymentHistory.setHinhThucThanhToan(payment);
        if (request.getLoaiThanhToan() == 0) {
            // tt luoon
            paymentHistory.setSoTien(request.getSoTien());
        } else {
            // suyx nghix theem
            paymentHistory.setSoTien(bill.getTongTienPhaiTra().add(bill.getTienShip()));
        }
        hoaDonHinhThucThanhToanRepository.save(paymentHistory);
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

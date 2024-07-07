package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.mapper.impl.LichSuHoaDonMapper;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.LichSuaHoaDonRepository;
import com.dantn.weblaptop.repository.NhanVienRepository;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LichSuHoaDonServiceImpl implements LichSuHoaDonService {
    LichSuaHoaDonRepository billHistoryRepository;
    HoaDonRepository billRepository;
    KhachHangRepository customerRepository;
    NhanVienRepository employeeRepository;

    @Override
    public LichSuHoaDonResponse create(CreateLichSuHoaDonRequest request) throws AppException {
        NhanVien existingEmployee = employeeRepository.findById(request.getIdNhanVien()).orElseThrow(
                () -> new AppException("Cant not find employee with ID : " + request.getIdNhanVien()));

        KhachHang existingCustomer = new KhachHang();
        if (request.getIdKhachHang() != null) {
            existingCustomer = customerRepository.findById(request.getIdKhachHang()).orElseThrow(
                    () -> new AppException("Cant not find customer with ID : " + request.getIdKhachHang()));
        }
        HoaDon existingBill = billRepository.findById(request.getIdHoaDon()).orElseThrow(
                () -> new AppException("Cant not find bill with ID : " + request.getIdHoaDon()));
        LichSuHoaDon newBillHistory = LichSuHoaDonMapper.createBillHistory(request);
        newBillHistory.setNhanVien(existingEmployee);
        newBillHistory.setKhachHang(existingCustomer);
        newBillHistory.setHoaDon(existingBill);
        LichSuHoaDonResponse response = LichSuHoaDonMapper.toBillHistoryResponse(billHistoryRepository.save(newBillHistory));

        log.info(response.toString());
        return response;
    }

    @Override
    public ResultPaginationResponse getBillHistoryByBillId(Long billId, Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<LichSuHoaDon> billHistoryPage = billHistoryRepository.findAllByHoaDonId(billId, pageable);

        // chuyển thanh response hay khong xem lại
        Page<LichSuHoaDonResponse> billHistoryPageResponses = billHistoryPage.map(
                billHistory -> LichSuHoaDonMapper.toBillHistoryResponse(billHistory));

        Meta meta = Meta.builder()
                .page(billHistoryPageResponses.getNumber())
                .pageSize(billHistoryPageResponses.getSize())
                .pages(billHistoryPageResponses.getTotalPages())
                .total(billHistoryPageResponses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(billHistoryPageResponses.getContent())
                .build();

        return response;
    }
}

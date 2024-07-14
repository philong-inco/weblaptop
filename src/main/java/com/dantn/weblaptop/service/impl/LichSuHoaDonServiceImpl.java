package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
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

import java.util.List;
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

        KhachHang existingCustomer = null;
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
    public List<LichSuHoaDonResponse> getBillHistoryByBillId(Long billId) {
        List<LichSuHoaDon> billHistoryList = billHistoryRepository.findAllByHoaDonId(billId);
        return billHistoryList.stream().map(
                billHistory -> LichSuHoaDonMapper.toBillHistoryResponse(billHistory)
        ).toList();
    }
}

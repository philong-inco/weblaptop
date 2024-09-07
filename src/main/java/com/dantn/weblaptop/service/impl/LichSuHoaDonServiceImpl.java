package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.RevertLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
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

import java.util.Collections;
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
                () -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        KhachHang existingCustomer = null;
        if (request.getIdKhachHang() != null) {
            existingCustomer = customerRepository.findById(request.getIdKhachHang()).orElseThrow(
                    () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        }
        HoaDon existingBill = billRepository.findById(request.getIdHoaDon()).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        LichSuHoaDon newBillHistory = LichSuHoaDonMapper.createBillHistory(request);
        newBillHistory.setNhanVien(existingEmployee);
        newBillHistory.setKhachHang(existingCustomer);
        newBillHistory.setHoaDon(existingBill);
        newBillHistory.setNguoiSua(existingEmployee.getTen());
        newBillHistory.setNguoiTao(existingEmployee.getTen());
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

    // Sua Nhan Vien
    @Override
    public void revertBillStatus(Long billId) throws AppException {
        // Lấy danh sách lịch sử hóa đơn theo ID hóa đơn
        List<LichSuHoaDon> histories = billHistoryRepository.findAllByHoaDonId(billId);

        if (histories.size() < 2) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        LichSuHoaDon lastHistory = histories.get(histories.size() - 2);
        if(lastHistory.getTrangThai()==0){
            throw new AppException(ErrorCode.UNACHIEVABLE);
        }
        HoaDon bill = lastHistory.getHoaDon();
        String currentStatus = bill.getTrangThai().getName();
        bill.setTrangThai(HoaDonStatus.getByIndex(lastHistory.getTrangThai()));
        HoaDon billResponse = billRepository.save(bill);

        LichSuHoaDon newBillHistory = new LichSuHoaDon();
        newBillHistory.setHoaDon(lastHistory.getHoaDon());
        newBillHistory.setKhachHang(lastHistory.getKhachHang());
        newBillHistory.setNhanVien(lastHistory.getNhanVien());
        newBillHistory.setTrangThai(lastHistory.getTrangThai());
        String changeDescription = "Nhân Viên " + lastHistory.getNhanVien().getTen() + " chuyển trạng thái từ " + currentStatus + " -> " + billResponse.getTrangThai().getName();
        newBillHistory.setNguoiTao(changeDescription);
        newBillHistory.setNguoiSua(changeDescription);
        billHistoryRepository.save(newBillHistory);
    }



}

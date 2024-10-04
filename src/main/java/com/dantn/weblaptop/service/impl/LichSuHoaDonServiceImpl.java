package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDon;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.LichSuHoaDonMapper;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.LichSuHoaDonRepository;
import com.dantn.weblaptop.repository.NhanVienRepository;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LichSuHoaDonServiceImpl implements LichSuHoaDonService {
    LichSuHoaDonRepository billHistoryRepository;
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
    public LichSuHoaDonResponse updateStatusBill(CreateLichSuHoaDon request, String billCode , Integer status) throws AppException {
//        Lấy từ sercurity
        NhanVien existingEmployee = employeeRepository.findById(1L).orElseThrow(
                () -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

//        KhachHang existingCustomer = null;
//        if (request.getIdKhachHang() != null) {
//            existingCustomer = customerRepository.findById(request.getIdKhachHang()).orElseThrow(
//                    () -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
//        }
        HoaDon existingBill = billRepository.findHoaDonByMa(billCode).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        LichSuHoaDon newBillHistory = new LichSuHoaDon();
        newBillHistory.setNhanVien(existingEmployee);
        newBillHistory.setKhachHang(existingBill.getKhachHang());
        newBillHistory.setHoaDon(existingBill);
        newBillHistory.setNguoiSua(existingEmployee.getTen());
        newBillHistory.setNguoiTao(existingEmployee.getTen());
        newBillHistory.setGhiChuChoKhachHang(request.getGhiChuKhachHang());
        newBillHistory.setGhiChuChoCuaHang(request.getGhiChuCuaHang());
        newBillHistory.setTrangThai(status);
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

    @Override
    public List<LichSuHoaDonResponse> getBillHistoryByBillCode(String code) {
        List<LichSuHoaDon> billHistoryList = billHistoryRepository.findAllByHoaDonMa(code);
        return billHistoryList.stream().map(
                billHistory -> LichSuHoaDonMapper.toBillHistoryResponse(billHistory)
        ).toList();
    }

    // Sua Nhan Vien
    @Override
    public void revertBillStatus(String codeBill) throws AppException {
        // Lấy danh sách lịch sử hóa đơn theo ID hóa đơn
        List<LichSuHoaDon> histories = billHistoryRepository.findAllByHoaDonMa(codeBill);

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

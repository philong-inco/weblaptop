package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.repository.SerialNumberDaBanRepository;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import com.dantn.weblaptop.service.SerialNumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberDaBanServiceImpl implements SerialNumberDaBanService {
    SerialNumberService serialNumberService;
    SerialNumberRepository serialNumberRepository;
    SerialNumberDaBanRepository serialNumberDaBanRepository;
    SanPhamChiTietRepository productDetailRepository;
    HoaDonRepository hoaDonRepository;

    LichSuHoaDonService billHistoryService;


    HoaDonRepository billRepository;

    @Override
    public List<SerialNumberDaBanResponse> getSerialNumberDaBanPage(Long billId) {
        List<SerialNumberDaBan> serialNumberSold = serialNumberDaBanRepository.findAllByHoaDonId(billId);
        List<SerialNumberDaBanResponse> serialNumberSoldResponses = new ArrayList<>();
        serialNumberSold.stream().map(
                serialNumberDaBan -> {
                    SerialNumberDaBanResponse response = new SerialNumberDaBanResponse();
                    response.setAnh("Ảnh");
                    // cần update
                    response.setGia(
                            serialNumberDaBan.getGiaBan() == null ?
                                    serialNumberDaBan.getSerialNumber().getSanPhamChiTiet().getGiaBan() :
                                    serialNumberDaBan.getGiaBan());
                    response.setTenSanPham(serialNumberDaBan.getSerialNumber().getSanPhamChiTiet().getSanPham().getTen());
                    response.setId(serialNumberDaBan.getId());
                    response.setHoaDonId(serialNumberDaBan.getHoaDon().getId());
                    return serialNumberSoldResponses.add(response);
                }
        ).toList();
        return serialNumberSoldResponses;
    }

    @Override
    public SerialNumberDaBanResponse create(CreateSerialNumberDaBanRequest request) throws AppException {
        HoaDon existingBill = hoaDonRepository.findById(request.getIdHoaDon()).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        List<SerialNumber> serialNumbers = serialNumberService.getSerialNumberByProductIdAndStatus(
                request.getIdSanPham(), 0);

        SerialNumberDaBan newSerialNumberDaBan = new SerialNumberDaBan();
        newSerialNumberDaBan.setHoaDon(existingBill);

        SerialNumber existingSerialNumber = null;
        if (serialNumbers.size() > 0) {
            existingSerialNumber = serialNumbers.get(0);
            newSerialNumberDaBan.setSerialNumber(existingSerialNumber);
        }

        SerialNumberDaBan serialNumberDaBan = serialNumberDaBanRepository.save(newSerialNumberDaBan);

        List<LichSuHoaDonResponse> existingHistories = billHistoryService.getBillHistoryByBillId(existingBill.getId());
        boolean hasStatus2 = existingHistories.stream().anyMatch(history -> history.getTrangThai() == 1);

        if (!hasStatus2) {
            CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
            billHistoryRequest.setIdHoaDon(existingBill.getId());
            billHistoryRequest.setTrangThai(1);
            // Cần sửa khi có security
            billHistoryRequest.setIdNhanVien(1L);
            // Lưu lịch sử hóa đơn
            try {
                billHistoryService.create(billHistoryRequest);
                existingBill.setTrangThai(HoaDonStatus.CHO_THANH_TOAN);
                billRepository.save(existingBill);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }

        SerialNumberDaBanResponse response = new SerialNumberDaBanResponse();
        response.setSerialNumberId(serialNumberDaBan.getSerialNumber().getId());
        response.setHoaDonId(serialNumberDaBan.getHoaDon().getId());

        return response;
    }

    @Override
    public void delete(Long id) {
        Optional<SerialNumberDaBan> optional = serialNumberDaBanRepository.findById(id);
        if (optional.isPresent()) {
            serialNumberDaBanRepository.delete(optional.get());
        }
    }
}

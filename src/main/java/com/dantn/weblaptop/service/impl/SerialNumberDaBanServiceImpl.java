package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.SerialNumberSoldMapper;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.repository.SerialNumberDaBanRepository;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import com.dantn.weblaptop.service.SerialNumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberDaBanServiceImpl implements SerialNumberDaBanService {
    SerialNumberService serialNumberService;
    SerialNumberDaBanRepository serialNumberDaBanRepository;
    SerialNumberRepository serialNumberRepository;
    HoaDonRepository hoaDonRepository;
    LichSuHoaDonService billHistoryService;

    @Override
    public List<SerialNumberDaBanResponse> getSerialNumberDaBanPage(String code) {
        List<SerialNumberDaBan> serialNumberSold = serialNumberDaBanRepository.findAllByHoaDonMa(code);
        Map<String, SerialNumberDaBanResponse> responseMap = new HashMap<>();

        for (SerialNumberDaBan serialNumberDaBan : serialNumberSold) {
            SerialNumberDaBanResponse.SerialInfo serialInfo = new SerialNumberDaBanResponse.SerialInfo(
                    serialNumberDaBan.getSerialNumber().getId(),
                    serialNumberDaBan.getSerialNumber().getMa()
            );
            SerialNumberDaBanResponse response =
                    SerialNumberSoldMapper.toSerialNumberDaBanResponse(serialNumberDaBan);
            String key = response.getBillId() + "-" + response.getProductDetailId();
            if (responseMap.containsKey(key)) {
                SerialNumberDaBanResponse existingResponse = responseMap.get(key);
                existingResponse.setQuantity(existingResponse.getQuantity() + 1);
                existingResponse.getSerialNumbers().add(serialInfo);
            } else {
                response.setQuantity(1);
                Set<SerialNumberDaBanResponse.SerialInfo> serialInfos = new HashSet<>();
                serialInfos.add(serialInfo);
                response.setSerialNumbers(serialInfos);
                responseMap.put(key, response);
            }
        }
        return new ArrayList<>(responseMap.values());
    }

    @Override
    @Transactional(rollbackFor = AppException.class)
    public Boolean create(CreateSerialNumberDaBanRequest request) throws AppException {
        HoaDon existingBill = hoaDonRepository.findHoaDonByMa(request.getBillCode()).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        List<SerialNumber> serialNumbers = request.getListSerialNumberId()
                .stream()
                .map(serialNumberId -> {
                    try {
                        return serialNumberRepository
                                .findById(serialNumberId).orElseThrow(
                                        () -> new AppException(ErrorCode.SERIAL_NUMBER_NOT_FOUND));
                    } catch (AppException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        List<Long> serialNumberInBill = serialNumberDaBanRepository.getAllSerialNumberInBillByBillCode(request.getBillCode());
        Set<Long> serialNumberInBillSet = new HashSet<>(serialNumberInBill);
        Set<Long> serialNumbersSet = serialNumbers.stream()
                .map(SerialNumber::getId)
                .collect(Collectors.toSet());

        // Lọc ra những SerialNumber chưa có trong danh sách để xóa
        List<Long> serialNumbersToDelete = serialNumberInBill.stream()
                .filter(id -> !serialNumbersSet.contains(id))
                .collect(Collectors.toList());

        // Xóa các SerialNumber không còn tồn tại từ cơ sở dữ liệu
        if (!serialNumbersToDelete.isEmpty()) {
            List<SerialNumberDaBan> serialNumberDaBansToDelete = serialNumberDaBanRepository.findAllBySerialNumberIdIn(serialNumbersToDelete);
            serialNumberDaBanRepository.deleteAll(serialNumberDaBansToDelete);
        }

        // Lấy tất cả các SerialNumberDaBan hiện có cho hóa đơn
        List<SerialNumberDaBan> existingSerialNumbersDaBan = serialNumberDaBanRepository.findAllByHoaDonId(existingBill.getId());

        // Lọc serialNumbers bỏ serialNumbe đã bị xóa
        List<SerialNumber> updatedSerialNumbers = serialNumbers.stream()
                .filter(serialNumber -> !serialNumberInBillSet.contains(serialNumber.getId()))
                .collect(Collectors.toList());

        // Tạo danh sách các SerialNumberDaBan để thêm mới
        List<SerialNumberDaBan> newSerialNumberDaBans = updatedSerialNumbers.stream()
                .map(serialNumber -> {
                    SerialNumberDaBan newSerialNumberDaBan = new SerialNumberDaBan();
                    newSerialNumberDaBan.setHoaDon(existingBill);
                    newSerialNumberDaBan.setSerialNumber(serialNumber);
                    return newSerialNumberDaBan;
                })
                .collect(Collectors.toList());

        if (!newSerialNumberDaBans.isEmpty()) {
            serialNumberDaBanRepository.saveAll(newSerialNumberDaBans);
        }

        List<LichSuHoaDonResponse> existingHistories = billHistoryService.getBillHistoryByBillId(existingBill.getId());
        boolean hasStatus = existingHistories.stream().anyMatch(history -> history.getTrangThai() == 1);

        if (!hasStatus) {
            CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
            billHistoryRequest.setIdHoaDon(existingBill.getId());
            billHistoryRequest.setTrangThai(1);
            // Cần sửa khi có security
            billHistoryRequest.setIdNhanVien(1L);
            // Lưu lịch sử hóa đơn
            try {
                billHistoryService.create(billHistoryRequest);
                existingBill.setTrangThai(HoaDonStatus.CHO_THANH_TOAN);
                hoaDonRepository.save(existingBill);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
//        tính tiền và check phiếu pgg
        prepareTheBill(existingBill.getMa());
        return true;
    }

    @Override
    public void delete(SerialNumberSoldDelete request) throws AppException {
        List<SerialNumberDaBan> serialNumbersToDelete = serialNumberDaBanRepository.findAllByIdInAndHoaDonMa(request.getSerialNumberIds(), request.getBillCode());
        if (!serialNumbersToDelete.isEmpty()) {
            String billCode = serialNumbersToDelete.get(0).getHoaDon().getMa();
            serialNumberDaBanRepository.deleteAll(serialNumbersToDelete);
            prepareTheBill(billCode);
        }
    }


    @Override
    public BigDecimal getBigDecimal(HoaDon hoaDon, List<SerialNumberDaBanResponse> listSerialNumberDaBan, HoaDonRepository hoaDonRepository) {
        PhieuGiamGia phieuGiamGia = hoaDon.getPhieuGiamGia();
        BigDecimal tongTien = listSerialNumberDaBan.stream()
                .map(response -> {
                    BigDecimal gia = response.getPrice() != null ? response.getPrice() : BigDecimal.ZERO;
                    int soLuong = response.getQuantity() != null ? response.getQuantity() : 0;
                    return gia.multiply(BigDecimal.valueOf(soLuong));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Tổng tiền : " + tongTien);
        hoaDon.setTongTienBanDau(tongTien);
        BigDecimal tienGiam = BigDecimal.ZERO;
        if (phieuGiamGia != null) {
//            check điều kiện pgg
            Integer loaiPGG = phieuGiamGia.getLoaiGiamGia();
            Integer trangThai = phieuGiamGia.getTrangThai();
            BigDecimal giaTraiPhieuGiam = phieuGiamGia.getGiaTriGiamGia();
            if (trangThai == 3 || trangThai == 2 || trangThai == 0) {
                System.out.println("PGG được hủy");
                hoaDon.setPhieuGiamGia(null);
                hoaDon.setTongTienPhaiTra(tongTien);
                hoaDonRepository.save(hoaDon);
                return tongTien;
            }
//            1 % : 2 VND
            if (loaiPGG == 2) {
                tienGiam = giaTraiPhieuGiam;
            } else {
//          tính % của phiếu giảm rồi trừ đi
                tienGiam = tongTien.multiply(giaTraiPhieuGiam).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            }
            System.out.println("Quy đổi : " + tienGiam);
            if (tongTien.compareTo(tienGiam) < 0) {
                tongTien = BigDecimal.ZERO;
            } else {
                tongTien = tongTien.subtract(tienGiam);
            }

        }
        hoaDon.setTongTienPhaiTra(tongTien);
        System.out.println("Tổng tiền sau giảm giá : " + hoaDon.getTongTienPhaiTra());

        hoaDonRepository.save(hoaDon);
        return tongTien;
    }

    //    hàm này gọi ở hàm trên
    private void prepareTheBill
    (String codeBill) {
        HoaDon hoaDon = hoaDonRepository.findHoaDonByMa(codeBill).get();
        List<SerialNumberDaBanResponse> listSerialNumberDaBan = getSerialNumberDaBanPage(codeBill);
        getBigDecimal(hoaDon, listSerialNumberDaBan, hoaDonRepository);
    }
}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.SerialNumberSoldMapper;
import com.dantn.weblaptop.repository.*;
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
    //    PhieuGiamGiaService phieuGiamGiaService;
    PhieuGiamGiaRepo phieuGiamGiaRepository;
    SanPhamChiTietRepository sanPhamChiTietRepository;
    SanPhamChiTietServiceImpl sanPhamChiTietService;
    private final SanPhamChiTietServiceImpl sanPhamChiTietServiceImpl;

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
            String key = response.getBillId() + "-" + response.getProductDetailId() + "-" + response.getPrice();
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
            List<Long> idsToDelete = serialNumberDaBansToDelete.stream()
                    .map(serialNumberDaBan -> serialNumberDaBan.getSerialNumber().getId())
                    .collect(Collectors.toList());
            serialNumberRepository.updateStatusByIdsNative(0, idsToDelete);
            serialNumberDaBanRepository.deleteAll(serialNumberDaBansToDelete);
        }

        // Lấy tất cả các SerialNumberDaBan hiện có cho hóa đơn
//        List<SerialNumberDaBan> existingSerialNumbersDaBan = serialNumberDaBanRepository.findAllByHoaDonId(existingBill.getId());

        // Lọc serialNumbers bỏ serialNumbe đã bị xóa
        List<SerialNumber> updatedSerialNumbers = serialNumbers.stream()
                .filter(serialNumber -> !serialNumberInBillSet.contains(serialNumber.getId()))
                .collect(Collectors.toList());

        // Tạo danh sách các SerialNumberDaBan để thêm mới
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(request.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
        FindSanPhamChiTietByFilter filter = new FindSanPhamChiTietByFilter();
        filter.setMaSanPhamChiTiet(sanPhamChiTiet.getMa());
        List<SanPhamChiTietClientDTO> sanPhamChiTietClientDTOS = sanPhamChiTietServiceImpl.findByFilter(filter);
        BigDecimal giaBan = BigDecimal.ZERO;
        if (!sanPhamChiTietClientDTOS.isEmpty()) {
            SanPhamChiTietClientDTO sanPhamChiTietDTO = sanPhamChiTietClientDTOS.get(0);
            String giaBanStr = sanPhamChiTietDTO.getGiaSauKhuyenMai();
            if (giaBanStr != null) {
                try {
                    giaBan = new BigDecimal(giaBanStr);
                } catch (NumberFormatException e) {
                    giaBan = new BigDecimal(sanPhamChiTietDTO.getGiaBan());
                }
            } else {
                giaBan = new BigDecimal(sanPhamChiTietDTO.getGiaBan());
            }
        }
        BigDecimal finalGiaBan = giaBan;

        List<SerialNumberDaBan> newSerialNumberDaBans = updatedSerialNumbers.stream()
                .map(serialNumber -> {
                    SerialNumberDaBan newSerialNumberDaBan = new SerialNumberDaBan();
                    newSerialNumberDaBan.setHoaDon(existingBill);
                    newSerialNumberDaBan.setGiaBan(finalGiaBan);
                    newSerialNumberDaBan.setSerialNumber(serialNumber);
                    return newSerialNumberDaBan;
                })
                .collect(Collectors.toList());

        if (!newSerialNumberDaBans.isEmpty()) {
            // update ở đây
            List<Long> idsToCreate = newSerialNumberDaBans.stream()
                    .map(serialNumberDaBan -> serialNumberDaBan.getSerialNumber().getId())
                    .collect(Collectors.toList());
            serialNumberRepository.updateStatusByIdsNative(1, idsToCreate);
            serialNumberDaBanRepository.saveAll(newSerialNumberDaBans);
        }
        // tính tiền
        Optional<BigDecimal> totalMoney = serialNumberDaBanRepository.sumGiaBanByHoaDonId(existingBill.getId());
        existingBill.setTongTienBanDau(totalMoney.orElse(BigDecimal.ZERO));
        System.out.println("0 Tỏng tiền : " + totalMoney.orElse(BigDecimal.ZERO));
        // check đổi khách hàng
        if (totalMoney.isPresent()) {
            BigDecimal total = totalMoney.get();
            if (existingBill.getPhieuGiamGia() == null) {
                System.out.println(" 0 Vào chọn phiếu : ");
                Optional<PhieuGiamGia> optional = getPhieuGiamGia(existingBill, total);
                if (optional.isPresent()) {
                    existingBill.setPhieuGiamGia(optional.get());
                    Optional<BigDecimal> optionalDiscountValue =
                            phieuGiamGiaRepository.findDiscountValue(total, optional.get().getId());
                    // tru
                    existingBill.setTongTienPhaiTra(total.subtract(optionalDiscountValue.orElse(BigDecimal.ZERO)) );
                    System.out.println("=> 0 Chọn đươc phiếu : quy đổi : "+optionalDiscountValue.get());
                    System.out.println("=> 0 phải trả : "+ existingBill.getTongTienPhaiTra());

                }else {
                    System.out.println("Ko đủ điều kiện :");
                    existingBill.setTongTienPhaiTra(total);
                }
            } else {
                // check khach hàng
                Long customId = existingBill.getPhieuGiamGia().getId();
                Optional<PhieuGiamGia> optional = phieuGiamGiaRepository.getVoucherByTotalAmountCustomerAndCoupon(
                        total, customId,existingBill.getPhieuGiamGia().getId());
                if(optional.isPresent()) {
                    System.out.println("=> 1 Đã có phiếu : ");
                    // Nếu hóa đơn đã có phiếu giảm giá, kiểm tra lại
                    Optional<PhieuGiamGia> optionalDiscountVoucher = getPhieuGiamGia(existingBill, total);
                    // Nếu không đủ tiền, xóa phiếu giảm giá
                    if (!optionalDiscountVoucher.isPresent()) {
                        // tổng tiền phỉa trả = ổng tiền ban đầu
                        existingBill.setTongTienPhaiTra(total);
                        existingBill.setPhieuGiamGia(null);
                        System.out.println("=> 2 Xóa phiếu : ");
                    } else {
                        // quy đổi max vs tính lại
                        Optional<BigDecimal> optionalDiscountValue =
                                phieuGiamGiaRepository.findDiscountValue(total, optionalDiscountVoucher.get().getId());
                        BigDecimal totalAmountDue = total.subtract(optionalDiscountValue.get());
                        existingBill.setTongTienPhaiTra(totalAmountDue);
                        System.out.println("=> 2Quy đổi : "+ optionalDiscountValue);
                    }
                }else{
                    System.out.println("3 : Khách ko có  của phiếu : ");
                    existingBill.setPhieuGiamGia(null);
                    existingBill.setTongTienPhaiTra(total);
                }

            }
        }else{
            System.out.println("=> Ko còn PS");
            existingBill.setPhieuGiamGia(null);
            existingBill.setTongTienPhaiTra(BigDecimal.ZERO);
        }
        hoaDonRepository.save(existingBill);
//        List<LichSuHoaDonResponse> existingHistories = billHistoryService.getBillHistoryByBillId(existingBill.getId());

        CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
        billHistoryRequest.setIdHoaDon(existingBill.getId());
        billHistoryRequest.setGhiChuChoCuaHang("Cập nhập sản phẩm của đơn hàng");
        billHistoryRequest.setTrangThai(1);
        // Cần sửa khi có security
        billHistoryRequest.setIdNhanVien(1L);
        // Lưu lịch sử hóa đơn
        try {
            billHistoryService.create(billHistoryRequest);
            //

        } catch (AppException e) {
            e.printStackTrace();
        }
//        }
//        tính tiền và check phiếu pgg
//        prepareTheBill(existingBill.getMa());
        return true;
    }

    @Override
    public void delete(SerialNumberSoldDelete request) throws AppException {
        List<SerialNumberDaBan> serialNumbersToDelete = serialNumberDaBanRepository.findAllByIdInAndHoaDonMa(request.getSerialNumberIds(), request.getBillCode());
        if (!serialNumbersToDelete.isEmpty()) {
            String billCode = serialNumbersToDelete.get(0).getHoaDon().getMa();

            serialNumberDaBanRepository.deleteAll(serialNumbersToDelete);
            List<Long> serialNumberIds = serialNumbersToDelete.stream()
                    .map(serialNumberDaBan -> serialNumberDaBan.getSerialNumber().getId())
                    .collect(Collectors.toList());
            List<SerialNumber> updatedSerialNumbers = serialNumberRepository.findAllById(serialNumberIds)
                    .stream()
                    .peek(serialNumber -> serialNumber.setTrangThai(0))
                    .collect(Collectors.toList());
            serialNumberRepository.saveAll(updatedSerialNumbers);
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

        // Nếu chưa có phiếu giảm giá, tìm và gán
        if (hoaDon.getPhieuGiamGia() == null) {
            Optional<PhieuGiamGia> optional = getPhieuGiamGia(hoaDon, tongTien);
            if (optional.isPresent()) {
                phieuGiamGia = optional.get();
                hoaDon.setPhieuGiamGia(phieuGiamGia);
            } else {
                hoaDon.setPhieuGiamGia(null);
            }
            hoaDonRepository.save(hoaDon);
        }

        Optional<HoaDon> newBill = hoaDonRepository.findHoaDonByMa(hoaDon.getMa());
        BigDecimal tienGiam = BigDecimal.ZERO;

        // Nếu đã có phiếu giảm giá
        if (newBill.get().getPhieuGiamGia() != null) {
            Optional<PhieuGiamGia> optional = getPhieuGiamGia(hoaDon, tongTien);
            // hàm xóa nếu tiền ko đủ
            if (optional.isPresent()) {
                phieuGiamGia = optional.get();
                hoaDon.setPhieuGiamGia(phieuGiamGia);
            } else {
                hoaDon.setPhieuGiamGia(null);
            }
            hoaDonRepository.save(hoaDon);

            // Check điều kiện PGG
            Integer loaiPGG = phieuGiamGia.getLoaiGiamGia();
            Integer trangThai = phieuGiamGia.getTrangThai();
            BigDecimal giaTriPhieuGiam = phieuGiamGia.getGiaTriGiamGia();

            if (trangThai == 3 || trangThai == 2 || trangThai == 0) {
                System.out.println("PGG được hủy");
                hoaDon.setPhieuGiamGia(null);
                hoaDon.setTongTienPhaiTra(tongTien);
                hoaDonRepository.save(hoaDon);
                return tongTien;
            }
            // Tính tiền giảm dựa vào loại PGG
            if (loaiPGG == 2) {
                tienGiam = giaTriPhieuGiam;
            } else {
                tienGiam = tongTien.multiply(giaTriPhieuGiam).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            }

            System.out.println("Quy đổi k : " + tienGiam);

            // Kiểm tra tông tiền sau giảm và giá trị giảm tối đa
            if (phieuGiamGia.getGiamToiDa().compareTo(tienGiam) < 0) {
                tienGiam = phieuGiamGia.getGiamToiDa();
            }
            if (tongTien.compareTo(tienGiam) < 0) {
                tongTien = BigDecimal.ZERO;
            } else {
                tongTien = tongTien.subtract(tienGiam);
            }
        }
        hoaDon.setTongTienPhaiTra(tongTien);
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

    private Optional<PhieuGiamGia> getPhieuGiamGia(HoaDon hoaDon, BigDecimal tongTien) {
        if (hoaDon.getKhachHang() == null) {
            System.out.println("Phiếu kh lẻ");
            return phieuGiamGiaRepository.getHighestDiscountVoucherByTotalAmount(tongTien);
        } else {
            System.out.println("Phiếu kh ht "+ hoaDon.getKhachHang().getId());
            return phieuGiamGiaRepository.getHighestDiscountVoucherByTotalAmountAndCustomer(tongTien, hoaDon.getKhachHang().getId());
        }
    }
}

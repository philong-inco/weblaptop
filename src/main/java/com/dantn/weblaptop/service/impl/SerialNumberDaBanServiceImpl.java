package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.constant.RankCustomer;
import com.dantn.weblaptop.dto.SerialNumberDaBan_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.LichSuHoaDonResponse;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    SanPhamChiTietServiceImpl sanPhamChiTietServiceImpl;
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    private final HinhThucThanhToanRepository hinhThucThanhToanRepository;

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
        List<Long> serialNumberInBill = serialNumberDaBanRepository.getAllSerialNumberInBillByBillId(existingBill.getId());
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
            existingBill.setTongSanPham(existingBill.getTongSanPham() - idsToDelete.size());
            serialNumberRepository.updateStatusByIdsNative(0, idsToDelete);
            serialNumberDaBanRepository.deleteAll(serialNumberDaBansToDelete);
        }

        // Lấy tất cả các SerialNumberDaBan hiện có cho hóa đơn
        List<Long> existingSerialNumbersDaBan = serialNumberDaBanRepository.getAllSerialNumberInBillByBillId(existingBill.getId());

        // Lọc serialNumbers bỏ serialNumbe đã bị xóa
        List<SerialNumber> updatedSerialNumbers = serialNumbers.stream()
                .filter(serialNumber -> !existingSerialNumbersDaBan.contains(serialNumber.getId()))
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
            // thêm mới ở đây
            List<Long> idsToCreate = newSerialNumberDaBans.stream()
                    .map(serialNumberDaBan -> serialNumberDaBan.getSerialNumber().getId())
                    .collect(Collectors.toList());
//            if (existingBill.getTrangThai() != HoaDonStatus.DON_MOI && existingBill.getLoaiHoaDon() == 1 && existingBill.getThanhToanSau() == 0) {
//                BigDecimal tienTraSau = newSerialNumberDaBans.stream()
//                        .map(SerialNumberDaBan::getGiaBan)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//                System.out.println("Tiền Trả Sau : " + tienTraSau);
//                // lấy ra số tiền đa bill ban đầu phụ thuộc vài soTien và tienThua
//                existingBill.setThanhToanSau(1);
//                HoaDonHinhThucThanhToan newHoaDonHinhThucThanhToan = new HoaDonHinhThucThanhToan();
//                newHoaDonHinhThucThanhToan.setSoTien(tienTraSau);
//                newHoaDonHinhThucThanhToan.setLoaiThanhToan(1);
//                newHoaDonHinhThucThanhToan.setHoaDon(existingBill);
//                hoaDonHinhThucThanhToanRepository.save(newHoaDonHinhThucThanhToan);
//            }
            existingBill.setTongSanPham(existingBill.getTongSanPham() + idsToCreate.size());
            serialNumberRepository.updateStatusByIdsNative(1, idsToCreate);
            serialNumberDaBanRepository.saveAll(newSerialNumberDaBans);
        }
        // tính tiền
        Optional<BigDecimal> totalMoney = serialNumberDaBanRepository.sumGiaBanByHoaDonId(existingBill.getId());
        existingBill.setTongTienBanDau(totalMoney.orElse(BigDecimal.ZERO));
        System.out.println("0 Tỏng tiền : " + totalMoney.orElse(BigDecimal.ZERO));
        // check đổi khách hàng
        calculateTotalAmountDue(existingBill, totalMoney);
        hoaDonRepository.save(existingBill);
        return true;
    }

    @Override
    public void delete(SerialNumberSoldDelete request) throws AppException {
        List<SerialNumberDaBan> serialNumbersToDelete = serialNumberDaBanRepository.findAllByIdInAndHoaDonMa(request.getSerialNumberIds(), request.getBillCode());
        if (!serialNumbersToDelete.isEmpty()) {
            HoaDon existingBill = hoaDonRepository.findHoaDonByMa(request.getBillCode()).orElseThrow(
                    () -> new AppException(ErrorCode.BILL_NOT_FOUND));
            existingBill.setTongSanPham(existingBill.getTongSanPham() - serialNumbersToDelete.size());
            serialNumberDaBanRepository.deleteAll(serialNumbersToDelete);
            List<Long> serialNumberIds = serialNumbersToDelete.stream()
                    .map(serialNumberDaBan -> serialNumberDaBan.getSerialNumber().getId())
                    .collect(Collectors.toList());
            List<SerialNumber> updatedSerialNumbers = serialNumberRepository.findAllById(serialNumberIds)
                    .stream()
                    .peek(serialNumber -> serialNumber.setTrangThai(0))
                    .collect(Collectors.toList());
            serialNumberRepository.saveAll(updatedSerialNumbers);
            // tính lại tiền
            // tính tiền
            Optional<BigDecimal> totalMoney = serialNumberDaBanRepository.sumGiaBanByHoaDonId(existingBill.getId());
            existingBill.setTongTienBanDau(totalMoney.orElse(BigDecimal.ZERO));
            System.out.println("0 Tỏng tiền : " + totalMoney.orElse(BigDecimal.ZERO));
            calculateTotalAmountDue(existingBill, totalMoney);
            hoaDonRepository.save(existingBill);
        }
    }

    @Override
    public List<SerialNumberDaBan_Dto> findSerialNumberDaBanTopSold(LocalDateTime startDate, LocalDateTime endDate) throws AppException {
        long startDateMillis = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endDateMillis = endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<Object[]> results = serialNumberDaBanRepository.findSerialNumberDaBanTopSold(startDateMillis, endDateMillis);
        if (!results.isEmpty()) {
            return results.stream().map(result -> {
                String productName = (String) result[0];
                Long productId = (Long) result[1];
                Long totalSerialsSold = (Long) result[2];

                return new SerialNumberDaBan_Dto(productName, productId, totalSerialsSold);
            }).collect(Collectors.toList());
        } else {
            throw new AppException(ErrorCode.SERIAL_NUMBER_DA_BAN);
        }
    }

    private Optional<PhieuGiamGia> getPhieuGiamGia(HoaDon hoaDon, BigDecimal tongTien) {
        if (hoaDon.getKhachHang() == null) {
            System.out.println("Phiếu kh lẻ");
            return phieuGiamGiaRepository.getHighestDiscountVoucherByTotalAmount(tongTien);
        } else {
            System.out.println("Phiếu kh ht " + hoaDon.getKhachHang().getId());
            return phieuGiamGiaRepository.getHighestDiscountVoucherByTotalAmountAndCustomer(tongTien, hoaDon.getKhachHang().getId());
        }
    }


    public void calculateTotalAmountDue(HoaDon existingBill, Optional<BigDecimal> totalMoney) {
        System.out.println("0 Tổng tiền : " + totalMoney.orElse(BigDecimal.ZERO));
        if (totalMoney.isPresent()) {
            BigDecimal total = totalMoney.get();

            if (existingBill.getPhieuGiamGia() == null) {
                Optional<PhieuGiamGia> optional = getPhieuGiamGia(existingBill, total);

                if (optional.isPresent()) {
                    existingBill.setPhieuGiamGia(optional.get());
                    Optional<BigDecimal> optionalDiscountValue = phieuGiamGiaRepository.findDiscountValue(total, optional.get().getId());
                    existingBill.setTongTienPhaiTra(total.subtract(optionalDiscountValue.orElse(BigDecimal.ZERO)));
                } else {
                    existingBill.setTongTienPhaiTra(total);
                }
            } else {
                Long customId = existingBill.getKhachHang() != null ? existingBill.getKhachHang().getId() : null;
                Optional<PhieuGiamGia> optional = phieuGiamGiaRepository.getVoucherByTotalAmountCustomerAndCoupon(
                        total, customId, existingBill.getPhieuGiamGia().getId());
                if (optional.isPresent()) {
                    Optional<BigDecimal> optionalDiscountValue = phieuGiamGiaRepository.findDiscountValue(total, optional.get().getId());
                    BigDecimal totalAmountDue = total.subtract(optionalDiscountValue.orElse(BigDecimal.ZERO));
                    existingBill.setTongTienPhaiTra(totalAmountDue);
                } else {
                    existingBill.setPhieuGiamGia(null);
                    existingBill.setTongTienPhaiTra(total);
                }
            }
        } else {
            existingBill.setPhieuGiamGia(null);
            existingBill.setTongTienPhaiTra(BigDecimal.ZERO);
        }
//        if (existingBill.getKhachHang() != null) {
//            BigDecimal rank = RankCustomer.getValueByRank(existingBill.getKhachHang().getHangKhachHang());
//            existingBill.setTienGiamHangKhachHang(rank);
//            BigDecimal newTongTienPhaiTra = existingBill.getTongTienPhaiTra().subtract(rank);
//
//            if (newTongTienPhaiTra.compareTo(BigDecimal.ZERO) < 0) {
//                newTongTienPhaiTra = BigDecimal.ZERO;
//            }
//            existingBill.setTongTienPhaiTra(newTongTienPhaiTra);
//        }
        if (existingBill.getTongTienPhaiTra().compareTo(BigDecimal.ZERO) < 0) {
            existingBill.setTongTienPhaiTra(BigDecimal.ZERO);
        }

        if ((existingBill.getTrangThai() != HoaDonStatus.DON_MOI) && existingBill.getLoaiHoaDon() == 1) {
            updatePayment(existingBill);
        }
    }

    public void updatePayment(HoaDon existingBill) {
        System.out.println("Vào");
        Optional<HoaDonHinhThucThanhToan> optionalTraSau =
                hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(existingBill.getId(), 1);
        Optional<HoaDonHinhThucThanhToan> optionalTraTruoc =
                hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(existingBill.getId(), 0);
        HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanRepository.findById(1L).get();
        // Cập nhật trạng thái thanh toán
        if (optionalTraTruoc.isPresent()) {
            existingBill.setThanhToanSau(1);
            // Tính tổng số tiền phải trả cộng với tiền ship
            BigDecimal tongTienPhaiTraVaShip = existingBill.getTongTienPhaiTra().add(existingBill.getTienShip());
            // Lấy số tiền đã thanh toán
            BigDecimal soTienDaThanhToan = optionalTraTruoc.get().getSoTien();

            // Tính số tiền chênh lệch
            BigDecimal soTienChenhLech = tongTienPhaiTraVaShip.subtract(soTienDaThanhToan);

            // Chỉ thực hiện lưu nếu có số tiền chênh lệch lớn hơn 0
            if (soTienChenhLech.compareTo(BigDecimal.ZERO) <= 0) {
                optionalTraSau.ifPresent(hoaDonHinhThucThanhToanRepository::delete);
            } else {
                // Chỉ thực hiện lưu nếu có số tiền chênh lệch lớn hơn 0
                HoaDonHinhThucThanhToan hoaDonHinhThucThanhToan = optionalTraSau.orElse(new HoaDonHinhThucThanhToan());
//                hoaDonHinhThucThanhToan.setSoTien(soTienChenhLech);
//                hoaDonHinhThucThanhToan.setTienNhan(BigDecimal.ZERO);
                hoaDonHinhThucThanhToan.setSoTien(optionalTraTruoc.get().getSoTien()); //
                hoaDonHinhThucThanhToan.setTienNhan(soTienChenhLech);
                hoaDonHinhThucThanhToan.setLoaiThanhToan(1);
                hoaDonHinhThucThanhToan.setTrangThai(1);
                hoaDonHinhThucThanhToan.setHoaDon(existingBill);
                hoaDonHinhThucThanhToan.setHinhThucThanhToan(hinhThucThanhToan);
                hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan);
            }
        } else {
            HoaDonHinhThucThanhToan hoaDonHinhThucThanhToan = optionalTraSau.orElse(new HoaDonHinhThucThanhToan());
            hoaDonHinhThucThanhToan.setSoTien(existingBill.getTongTienPhaiTra().add(existingBill.getTienShip()));
            hoaDonHinhThucThanhToan.setTienNhan(existingBill.getTongTienPhaiTra().add(existingBill.getTienShip()));
            hoaDonHinhThucThanhToan.setLoaiThanhToan(1);
//
            hoaDonHinhThucThanhToan.setTrangThai(1);
            hoaDonHinhThucThanhToan.setHinhThucThanhToan(hinhThucThanhToan);
//
            hoaDonHinhThucThanhToan.setHoaDon(existingBill);
            hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan);
        }
    }

}

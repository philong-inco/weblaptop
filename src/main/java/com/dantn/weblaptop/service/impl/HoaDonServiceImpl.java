package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDonRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.*;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.hoadon.HoaDonHinhThucThanhToan;
import com.dantn.weblaptop.entity.hoadon.LichSuHoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.HoaDonMapper;
import com.dantn.weblaptop.repository.*;
import com.dantn.weblaptop.service.DiaChi_Service;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import com.dantn.weblaptop.util.BillUtils;
import com.dantn.weblaptop.util.GenerateCode;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {
    HoaDonRepository billRepository;
    LichSuHoaDonService billHistoryService;
    LichSuHoaDonRepository billHistoryRepository;
    NhanVienRepository employeeRepository;
    SerialNumberDaBanService serialNumberDaBanService;
    SerialNumberDaBanRepository serialNumberDaBanRepository;
    KhachHangRepository customerRepository;
    PhieuGiamGiaRepo couponRepository;
    DiaChi_Repository addressRepository;
    HinhThucThanhToanRepository hinhThucThanhToanRepository;
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    SerialNumberRepository serialNumberRepository;
    DiaChiService_Implement diaChiService;

    @Override
    public ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize), Sort.by("id").descending());
        Page<HoaDon> billHistoryPage = billRepository.findAll(pageable);
//        Page<HoaDonResponse> responses = billHistoryPage.map(bill -> HoaDonMapper.toHoaDonResponse(bill));
        Page<HoaDonResponse> responses = billHistoryPage.map(bill -> {
            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(bill);
            Optional<Integer> quantity = serialNumberDaBanRepository.getQuantityByHoaDonId(bill.getId());
            response.setTongSanPham(
                    quantity.orElse(0));
            return response;
        });
        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())
                .build();
        return response;
    }

    @Override
    public HoaDonResponse createBill() throws AppException {
        List<HoaDon> billListStatusPending = billRepository.findByTrangThaiAndLoaiHoaDon(HoaDonStatus.getHoaDonStatusEnum("Đơn mới"), 0);
//        if (billListStatusPending.size() >= 5) {
//            throw new AppException(ErrorCode.MAXIMUM_5);
//        }
        NhanVien existingEmployee = employeeRepository.findById(1L).get();
        // save
        HoaDon newBill = new HoaDon();
        newBill.setMa(GenerateCode.generateHoaDon());
        newBill.setNhanVien(existingEmployee);
        newBill.setTongTienPhaiTra(BigDecimal.ZERO);
        newBill.setTongTienBanDau(BigDecimal.ZERO);
        newBill.setTrangThai(HoaDonStatus.DON_MOI);
        newBill.setLoaiHoaDon(0);// 0 : Tại quầy - 1 : Online
        newBill.setTienShip(BigDecimal.ZERO);
        newBill.setTienGiamHangKhachHang(BigDecimal.ZERO);
        newBill.setThanhToanSau(0);// 0 tt luôn : 1 là sau
        Optional<HoaDon> exitingBill = billRepository.findHoaDonByMa(newBill.getMa());
        if (exitingBill.isPresent()) {
            newBill.setMa(GenerateCode.generateHoaDon());
        }
        HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(newBill));

        CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
        billHistoryRequest.setIdHoaDon(response.getId());
        // TT LSHD : 0 -tạo mới | 1 - Cập nhập : ||  2 - thanh toán :
        billHistoryRequest.setTrangThai(0);
        // sủa khi có security
        billHistoryRequest.setIdNhanVien(1L);
        // save
        billHistoryService.create(billHistoryRequest);
        return response;
    }

    @Override
    public HoaDonResponse updateBill(Long id, UpdateHoaDonRequest request) {
        return null;
    }

    @Override
    public HoaDonResponse updateBillByCode(String code, UpdateHoaDonRequest request) {
        return null;
    }

    @Override
    public HoaDonResponse getBillById(Long id) throws AppException {
        HoaDon existingBill = billRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        return HoaDonMapper.toHoaDonResponse(existingBill);
    }

    @Override
    public HoaDonResponse getBillByCode(String code) throws AppException {
        HoaDon existingBill = billRepository.findHoaDonByMa(code).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND));
        HoaDonResponse response = HoaDonMapper.toHoaDonResponse(existingBill);
        Optional<Integer> quantity = serialNumberDaBanRepository.getQuantityByHoaDonId(existingBill.getId());
        response.setTongSanPham(quantity.orElse(0));
        if (response.getDiaChi() != null) {
            String[] diaChiParts = response.getDiaChi().split("\\|");
            for (int i = 0; i < diaChiParts.length; i++) {
                diaChiParts[i] = diaChiParts[i].trim();
            }
            if (diaChiParts.length == 4) {
                String diaChiChiTiet = diaChiParts[0];
                String phuongXa = diaChiParts[1];
                String quanHuyen = diaChiParts[2];
                String tinhThanh = diaChiParts[3];
                response.setDiaChi(diaChiChiTiet);
                response.setPhuong(phuongXa);
                response.setHuyen(quanHuyen);
                response.setTinh(tinhThanh);
            } else {
                System.out.println("Chuỗi địa chỉ không có đủ 4 phần.");
            }}
        return response;
    }

    @Override
    public HoaDonResponse getBillByIdAndStatus(Long id, String status) {
        HoaDon bill = billRepository.findByIdAndTrangThai(id, HoaDonStatus.getHoaDonStatusEnum(status)).orElse(null);
        if (bill != null) {
            return HoaDonMapper.toHoaDonResponse(bill);
        }
        return null;
    }

    @Override
    public ResultPaginationResponse pageBillByStatusAndType(String status, Integer type, Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize), Sort.by("id").descending());
        HoaDonStatus billStatus = HoaDonStatus.getHoaDonStatusEnumByKey(status);
        Page<HoaDon> billPage = billRepository.findByTrangThaiAndLoaiHoaDon(billStatus, type, pageable);
        Page<HoaDonResponse> responses = billPage.map(bill -> HoaDonMapper.toHoaDonResponse(bill));

        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())
                .build();
        return response;
    }

    @Override
    public void updateStatus(String code, String status) throws AppException {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
        if (optional.isPresent()) {
            HoaDon bill = optional.get();
            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            CreateLichSuHoaDonRequest billHistoryRequest = new CreateLichSuHoaDonRequest();
            billHistoryRequest.setIdHoaDon(bill.getId());
            billHistoryRequest.setTrangThai(BillUtils.convertBillStatusEnumToInteger(bill.getTrangThai()));
            // sủa khi có security
            billHistoryRequest.setIdNhanVien(1L);
            // save
            billHistoryService.create(billHistoryRequest);
            billRepository.save(bill);
        }
    }

    @Override
    public ResultPaginationResponse filterHoaDon(Specification<HoaDon> specification, Pageable pageable) {
        Page<HoaDon> billPage = billRepository.findAll(specification, pageable);
//        Page<HoaDonResponse> responses = billPage.map(
//                HoaDonMapper::toHoaDonResponse
//        );

        Page<HoaDonResponse> responses = billPage.map(bill -> {
            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(bill);
            Optional<Integer> quantity = serialNumberDaBanRepository.getQuantityByHoaDonId(bill.getId());
            response.setTongSanPham(quantity.orElse(0));
            return response;
        });

        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        return ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())
                .build();
    }

    //Lặp code bên .. ĐÃ bán
    public BigDecimal prepareTheBill(String codeBill) {
        HoaDon hoaDon = billRepository.findHoaDonByMa(codeBill).get();
        List<SerialNumberDaBanResponse> listSerialNumberDaBan = serialNumberDaBanService.getSerialNumberDaBanPage(codeBill);
        return serialNumberDaBanService.getBigDecimal(hoaDon, listSerialNumberDaBan, billRepository);
    }

    @Override
    public void deleteBillByCode(String code) {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
        if (optional.isPresent()) {
            optional.get().setTrangThai(HoaDonStatus.XOA);
            billRepository.save(optional.get());
        }
    }

    @Override
    public HoaDonResponse addCustomerToBill(Long customerId, String billCode) throws AppException {
        Optional<KhachHang> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            Optional<HoaDon> bill = billRepository.findHoaDonByMa(billCode);
            if (bill.isPresent()) {
                KhachHang existingCustomer = customer.get();
                HoaDon existingBill = bill.get();
                existingBill.setKhachHang(existingCustomer);

                DiaChi_Response diaChiResponse = diaChiService.getDiaChiDefauldOfIdKhachHang(existingCustomer.getId());
                existingBill.setEmail(diaChiResponse.getEmailNguoiNhan());
                existingBill.setSdt(diaChiResponse.getSdtNguoiNhan());
                existingBill.setDiaChi(
                        diaChiResponse.getDiaChiNhanHang() + " | "
                                + diaChiResponse.getIdPhuongXa() + " | "
                                + diaChiResponse.getIdQuanHuyen() + " | "
                                + diaChiResponse.getIdTinhThanhPho());
                return HoaDonMapper.toHoaDonResponse(billRepository.save(bill.get()));
            } else {
                throw new AppException(ErrorCode.BILL_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
    }

    @Override
    public HoaDonResponse addCouponToBill(Long couponId, String billCode) throws AppException {
        HoaDon existingBill = billRepository.findHoaDonByMa(billCode).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND)
        );
        PhieuGiamGia coupon = couponRepository.findById(couponId).orElseThrow(
                () -> new AppException(ErrorCode.COUPONS_NOT_FOUND));
//        BigDecimal moneyReduced = BigDecimal.ZERO;
//        // 1 % : 2 VND
//        if (coupon.getLoaiGiamGia() == 2) {
//            moneyReduced = coupon.getGiaTriGiamGia();
//        } else {
////  tính % của phiếu giảm rồi trừ đi
//            moneyReduced = existingBill.getTongTienBanDau()
//                    .multiply(coupon.getGiaTriGiamGia())
//                    .divide(BigDecimal.valueOf(100),RoundingMode.HALF_UP);
//        }
//        if(coupon.getGiamToiDa().compareTo(moneyReduced) < 0) {
//            moneyReduced = coupon.getGiamToiDa();
//        }
//        if (existingBill.getTongTienBanDau().compareTo(moneyReduced) < 0) {
//            existingBill.setTongTienPhaiTra(BigDecimal.ZERO);
//        } else {
//            BigDecimal totalMoney = existingBill.getTongTienBanDau().subtract(moneyReduced);
//            existingBill.setTongTienPhaiTra(totalMoney);
//        }
//        existingBill.setPhieuGiamGia(coupon);
        BigDecimal moneyReduced = calculateDiscount(existingBill, coupon);
        calculateDiscount(existingBill, coupon);
        updateTotalMoney(existingBill, moneyReduced);
        existingBill.setPhieuGiamGia(coupon);
        return HoaDonMapper.toHoaDonResponse(billRepository.save(existingBill));
    }

    @Override
    public HoaDonResponse addCouponToBillByCode(String couponCode, String billCode) throws AppException {
        HoaDon existingBill = billRepository.findHoaDonByMa(billCode).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND)
        );
        PhieuGiamGia coupon = couponRepository.findByMa(couponCode.trim().toUpperCase()).orElseThrow(
                () -> new AppException(ErrorCode.COUPONS_NOT_FOUND));
        boolean isCustomerNull = existingBill.getKhachHang() == null;
        Optional<PhieuGiamGia> optional = isCustomerNull
                ? couponRepository.getByTotalAmountAndCouponCode(existingBill.getTongTienBanDau(), coupon.getMa())
                : couponRepository.getAllByTotalAmountAndCustomerAndCouponCode(
                existingBill.getTongTienBanDau(),
                existingBill.getKhachHang().getId(),
                coupon.getMa()
        );

        if (!optional.isPresent()) {
            throw new AppException(ErrorCode.COUPON_DOES_NOT_APPLY);
        }

        BigDecimal moneyReduced = calculateDiscount(existingBill, coupon);
        updateTotalMoney(existingBill, moneyReduced);
        existingBill.setPhieuGiamGia(coupon);
        return HoaDonMapper.toHoaDonResponse(billRepository.save(existingBill));
    }

    @Override
    public Boolean payCounter(String billCode, UpdateHoaDonRequest request) throws AppException {
        HoaDon bill = billRepository.findHoaDonByMa(billCode.trim()).orElseThrow(
                () -> new AppException(ErrorCode.BILL_NOT_FOUND)
        );
        Optional<Integer> quantityInBill = serialNumberDaBanRepository.getQuantityByHoaDonId(bill.getId());
        if(quantityInBill.get()==0){
            throw new AppException(ErrorCode.BILL_WITHOUT_PRODUCT);
        }



//        List<Long> serialInBill = serialNumberDaBanRepository.getSerialNumberInBillId(bill.getId());

        // up lại các trạng thái của serial sang đã bán
//        serialNumberRepository.updateStatusByInIds(serialInBill);
        // up lại tổng tiền
//        billRepository.updateTotalMoneyByBillCode(bill.getMa());
        // xóa các serial ở hóa đươn khác khác
//        serialNumberDaBanRepository.deleteAllNotBillId(bill.getId(), serialInBill);
        // xóa phiếu pgg ở bill !=
        billRepository.deleteCouponInBill();

        // trừ phiếu giảm giá
        if (bill.getPhieuGiamGia() != null) {
            Optional<PhieuGiamGia> couponOptional = couponRepository.findById(bill.getPhieuGiamGia().getId());
            if (couponOptional.isPresent()) {
                PhieuGiamGia coupon = couponOptional.get();
                Integer quantity = coupon.getSoLuong() - 1;
                coupon.setSoLuong(quantity);
                PhieuGiamGia exitingCoupon = couponRepository.save(coupon);
                if (exitingCoupon.getSoLuong() == 0) {
                    exitingCoupon.setTrangThai(3);
                    couponRepository.save(exitingCoupon);
                }
            }
        }
        savePaymentMethod(request.getChuyenKhoan(), bill);
        savePaymentMethod(request.getTienMat(), bill);

//        tại quầy ko ship
        if (request.getLoaiHoaDon() == 0) {
            bill.setTrangThai(HoaDonStatus.HOAN_THANH);
            billRepository.save(bill);
            LichSuHoaDon billHistory = new LichSuHoaDon();
            billHistory.setHoaDon(bill);
            billHistory.setTrangThai(6);
            billHistory.setGhiChuChoCuaHang("Thanh toán thành công");
            billHistory.setGhiChuChoKhachHang("Thanh toán thành công");
            NhanVien nhanVien = employeeRepository.findById(1L).get();
            billHistory.setNhanVien(nhanVien);
            billHistoryRepository.save(billHistory);
            return true;
        }
        if (request.getLoaiHoaDon() == 1) {
            bill.setTrangThai(HoaDonStatus.CHO_GIAO);
            bill.setLoaiHoaDon(request.getLoaiHoaDon());
            bill.setSdt(request.getSdt());
            bill.setEmail(request.getEmail());
            bill.setDiaChi(
                    request.getDiaChi() + " , "
                    + request.getTenPhuong() + " , "
                    + request.getTenHuyen() + " , "
                    + request.getTenTinh() + " | "
                    + request.getPhuong() + " | "
                    + request.getHuyen() + " | "
                    + request.getTinh());
            bill.setGhiChu(request.getGhiChu());
            billRepository.save(bill);
            LichSuHoaDon billHistory = new LichSuHoaDon();
            billHistory.setHoaDon(bill);
            billHistory.setTrangThai(6);
            billHistory.setGhiChuChoCuaHang("Hóa đơn được xác nhận và chờ giao");
            billHistory.setGhiChuChoKhachHang("Hóa đơn được xác nhận và chờ giao");
            NhanVien nhanVien = employeeRepository.findById(1L).get();
            billHistory.setNhanVien(nhanVien);
            billHistoryRepository.save(billHistory);
            return true;
        }
        return true;
    }

    private BigDecimal calculateDiscount(HoaDon existingBill, PhieuGiamGia coupon) {
        BigDecimal moneyReduced = BigDecimal.ZERO;
        // 1 % : 2 VND
        if (coupon.getLoaiGiamGia() == 2) {
            moneyReduced = coupon.getGiaTriGiamGia();
        } else {
            // Tính % của phiếu giảm rồi trừ đi
            moneyReduced = existingBill.getTongTienBanDau()
                    .multiply(coupon.getGiaTriGiamGia())
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        }

        if (coupon.getGiamToiDa().compareTo(moneyReduced) < 0) {
            moneyReduced = coupon.getGiamToiDa();
        }
        return moneyReduced;
    }

    private void updateTotalMoney(HoaDon existingBill, BigDecimal moneyReduced) {
        if (existingBill.getTongTienBanDau().compareTo(moneyReduced) < 0) {
            existingBill.setTongTienPhaiTra(BigDecimal.ZERO);
        } else {
            BigDecimal totalMoney = existingBill.getTongTienBanDau().subtract(moneyReduced);
            existingBill.setTongTienPhaiTra(totalMoney);
        }
    }

    private Optional<PhieuGiamGia> getPhieuGiamGia(HoaDon hoaDon, BigDecimal tongTien) {
        if (hoaDon.getKhachHang() == null) {
            return couponRepository.getHighestDiscountVoucherByTotalAmount(tongTien);
        } else {
            return couponRepository.getHighestDiscountVoucherByTotalAmountAndCustomer(tongTien, hoaDon.getKhachHang().getId());
        }
    }

    private void savePaymentMethod(Long paymentMethodId, HoaDon bill) throws AppException {
        if (paymentMethodId != null) {
            HinhThucThanhToan httt = hinhThucThanhToanRepository.findById(paymentMethodId).orElseThrow(
                    () -> new AppException(ErrorCode.PAY_NO_FOUND)
            );
            HoaDonHinhThucThanhToan hoaDonHinhThucThanhToan = new HoaDonHinhThucThanhToan();
            hoaDonHinhThucThanhToan.setHoaDon(bill);
            hoaDonHinhThucThanhToan.setHinhThucThanhToan(httt);
            hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan);
        }
    }
}

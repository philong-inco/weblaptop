package com.dantn.weblaptop.hoadon.service.impl;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.hoadon.dto.request.HoaDonRequest;
import com.dantn.weblaptop.hoadon.dto.request.LichSuHoaDonRquest;
import com.dantn.weblaptop.hoadon.dto.response.HoaDonResponse;
import com.dantn.weblaptop.hoadon.dto.response.Meta;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;
import com.dantn.weblaptop.hoadon.fake.KhachHangRepositoryFAKE;
import com.dantn.weblaptop.hoadon.fake.NhanVienRepositoryFAKE;
import com.dantn.weblaptop.hoadon.fake.PhieuGiamGiaRepositoryFAKE;
import com.dantn.weblaptop.hoadon.mapper.HoaDonMapper;
import com.dantn.weblaptop.hoadon.repository.HoaDonRepository;
import com.dantn.weblaptop.hoadon.service.HoaDonService;
import com.dantn.weblaptop.hoadon.service.LichSuHoaDonService;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {

    LichSuHoaDonService billHistoryService;

    HoaDonRepository billRepository;

    PhieuGiamGiaRepositoryFAKE voucherRepository;

    NhanVienRepositoryFAKE employeeRepository;

    KhachHangRepositoryFAKE customerRepository;

    GenerateCode generateCode;

    @Override
    public ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";

        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<HoaDon> billHistoryPage = billRepository.findAll(pageable);
        Page<HoaDonResponse> billPageResponse = billHistoryPage.map(
                bill -> HoaDonMapper.toBillResponse(bill));

        Meta meta = Meta.builder()
                .page(billPageResponse.getNumber())
                .pageSize(billPageResponse.getSize())
                .pages(billPageResponse.getTotalPages())
                .total(billPageResponse.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(billPageResponse.getContent())
                .build();
        return response;
    }

    @Override
    public HoaDonResponse createBill(HoaDonRequest request) throws AppException {
        // ma , NV , KH , maPGG , ||  tongTienPhaiTra , ngayMongMuonNhanHang ?
        NhanVien existingEmployee = employeeRepository.findById(request.getIdNhanVien()).orElseThrow(
                () -> new AppException("Cant not find employee with ID : " + request.getIdNhanVien()));
        KhachHang existingCustomer = customerRepository.findById(request.getIdKhachHang()).orElse(null);
        PhieuGiamGia existingVoucher = voucherRepository.findByMa(request.getMaPhieuGiamGia()).orElse(null);

        HoaDon newBill = HoaDonMapper.createBill(request);
        newBill.setNhanVien(existingEmployee);
        newBill.setKhachHang(existingCustomer);
        newBill.setPhieuGiamGia(existingVoucher);
        //

        newBill.setMa(generateCode.generateCode(GenerateCode.BILL_PREFIX));

        HoaDon bill = billRepository.save(newBill);
        LichSuHoaDonRquest billHistoryRequest = new LichSuHoaDonRquest();
        billHistoryRequest.setIdHoaDon(bill.getId());
        billHistoryRequest.setIdKhachHang(bill.getKhachHang()!=null ? bill.getKhachHang().getId() : null);
        billHistoryRequest.setIdNhanVien(bill.getNhanVien().getId());
        billHistoryRequest.setGhiChuChoCuaHang(request.getGhiChuChoCuaHang());
        billHistoryRequest.setGhiChuChoKhachHang(request.getGhiChuChoKhachHang());
        billHistoryRequest.setTrangThai(0);// 0 tao mới
        billHistoryService.create(billHistoryRequest);

        HoaDonResponse response = HoaDonMapper.toBillResponse(bill);
        return null;
    }

    @Override
    public HoaDonResponse updateBill(Long id, HoaDonRequest request) {
        return null;
    }

    @Override
    public Boolean updateBillStatus(Long id, HoaDonStatus status) {
        return null;
    }

    private void calculateTotalAmount(HoaDon bill, HoaDonRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (bill.getPhieuGiamGia() == null) {
// tính toán tổng tiền và tiền ban đầu
        } else {

        }
    }
}

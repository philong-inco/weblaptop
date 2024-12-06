package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.config.JwtUtil;
import com.dantn.weblaptop.constant.HoaDonStatus;
import com.dantn.weblaptop.constant.RankCustomer;
import com.dantn.weblaptop.dto.HoaDonDashboard_Dto;
import com.dantn.weblaptop.dto.TrangThaiHoaDon_Dto;
import com.dantn.weblaptop.dto.request.create_request.*;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChiHoaDonRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.*;
import com.dantn.weblaptop.dto.response.pdf.BillPdfResponse;
import com.dantn.weblaptop.dto.response.pdf.HoaDonHinhThucThanhToanPdfReponse;
import com.dantn.weblaptop.dto.response.pdf.SerialNumberDaBanPdfResponse;
import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.hoadon.*;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.HoaDonHinhThucThanhToanMapper;
import com.dantn.weblaptop.mapper.impl.HoaDonMapper;
import com.dantn.weblaptop.mapper.impl.SerialNumberSoldMapper;
import com.dantn.weblaptop.repository.*;
import com.dantn.weblaptop.service.*;
import com.dantn.weblaptop.util.BillUtils;
import com.dantn.weblaptop.util.GenerateCode;
import com.dantn.weblaptop.util.SendEmailBill;
import com.google.zxing.WriterException;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.lowagie.text.pdf.BaseFont;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {
    //    private final ModelMapper modelMapper;
    HoaDonRepository billRepository;
    LichSuHoaDonService billHistoryService;
    LichSuHoaDonRepository billHistoryRepository;
    NhanVien_Repositoy employeeRepository;
    SerialNumberDaBanService serialNumberDaBanService;
    SerialNumberDaBanRepository serialNumberDaBanRepository;
    KhachHangRepository customerRepository;
    PhieuGiamGiaRepo couponRepository;
    DiaChi_Repository addressRepository;
    HinhThucThanhToanRepository hinhThucThanhToanRepository;
    HoaDonHinhThucThanhToanRepository hoaDonHinhThucThanhToanRepository;
    SerialNumberRepository serialNumberRepository;
    DiaChiService_Implement diaChiService;
    HoaDonHinhThucThanhToanSerive hoaDonHinhThucThanhToanSerive;
    SendEmailBill sendEmailBill;
    KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;
    SanPhamChiTietRepository sanPhamChiTietRepository;
    GioHangChiTietService gioHangChiTietService;
    SpringTemplateEngine templateEngine;
    GioHangRepository gioHangRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final HoaDonRepository hoaDonRepository;
    HinhThucThanhToanService hinhThucThanhToanService;
    private final SerialNumberService serialNumberService;

    @Override
    public ResultPaginationResponse getBillPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize), Sort.by("id").descending());
        Page<HoaDon> billHistoryPage = billRepository.findAll(pageable);
        Page<HoaDonResponse> responses = billHistoryPage.map(bill -> {
            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(bill);
//            Optional<Integer> quantity = serialNumberDaBanRepository.getQuantityByHoaDonId(bill.getId());
//            response.setTongSanPham(
//                    quantity.orElse(0));
            return response;
        });
        Meta meta = Meta.builder().page(responses.getNumber()).pageSize(responses.getSize()).pages(responses.getTotalPages()).total(responses.getTotalElements()).build();

        ResultPaginationResponse response = ResultPaginationResponse.builder().meta(meta).result(responses.getContent()).build();
        return response;
    }

    @Override
    public HoaDonResponse createBill() throws AppException {
//        List<HoaDon> billListStatusPending = billRepository.findByTrangThaiAndLoaiHoaDon(HoaDonStatus.getHoaDonStatusEnum("Đơn mới"), 0);
//        if (billListStatusPending.size() >= 5) {
//            throw new AppException(ErrorCode.MAXIMUM_5);
//        }
        HoaDon newBill = new HoaDon();
        Optional<String> emailOption = JwtUtil.getCurrentUserLogin();
        if (emailOption.isPresent() && !emailOption.get().equals("anonymousUser")) {
            NhanVien existingEmployee = employeeRepository.findByEmail(emailOption.get());
            newBill.setNhanVien(existingEmployee);
        }

        // save

        newBill.setMa(GenerateCode.generateHoaDon());

        newBill.setTongTienPhaiTra(BigDecimal.ZERO);
        newBill.setTongTienBanDau(BigDecimal.ZERO);
        newBill.setTrangThai(HoaDonStatus.DON_MOI);
        newBill.setLoaiHoaDon(0);// 0 : Tại quầy - 1 : Online
        newBill.setTongSanPham(0);
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
    public String updateBill(String code, String status, String tran) {
        HoaDon hoaDon = billRepository.findHoaDonByMa(code).get();
        if (status.equals("00")) {
            hoaDon.setTrangThai(HoaDonStatus.XAC_NHAN);
            hoaDonRepository.save(hoaDon);
            List<SerialNumber> serials = serialNumberRepository.getListSerialInBill(hoaDon.getId());
            serials.forEach(serialNumber -> serialNumber.setTrangThai(1));
            serialNumberRepository.saveAll(serials);
            LichSuHoaDon billHistory = new LichSuHoaDon();
            billHistory.setHoaDon(hoaDon);
            billHistory.setTrangThai(9);
//            billHistory.setGhiChuChoCuaHang("Thanh toán thành công");
            billHistory.setGhiChuChoKhachHang("Thanh toán thành công");
            billHistoryRepository.save(billHistory);
            Optional<HoaDonHinhThucThanhToan> optional = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndTrangThaiVsPTTT(hoaDon.getId(), 1, 2L);
            if (optional.isPresent()) {
                optional.get().setMaGioDich(tran);
                optional.get().setTrangThai(0);
                hoaDonHinhThucThanhToanRepository.save(optional.get());
            }

            // cập nhập lại lịch sử
//            gửi mail
            return "Oke";
        } else {
            // hoàn lại serial
            hoaDon.setTrangThai(HoaDonStatus.HUY);
            hoaDonRepository.save(hoaDon);
//            Optional<HoaDonHinhThucThanhToan> optional =
//                    hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndTrangThaiVsPTTT(hoaDon.getId(),1,2L);
//            optional.get().setTrangThai(1);
//            hoaDonHinhThucThanhToanRepository.save(optional.get());
            productRefund(hoaDon);
            // hoàn phiếu pgg
            return "Đã hoàn lại serial";
        }
    }

    @Override
    public HoaDonResponse updateBillByCode(String code, UpdateHoaDonRequest request) {
        return null;
    }

    @Override
    public HoaDonResponse getBillById(Long id) throws AppException {
        HoaDon existingBill = billRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        return HoaDonMapper.toHoaDonResponse(existingBill);
    }

    @Override
    public HoaDonResponse getBillByCode(String code) throws AppException {
        HoaDon existingBill = billRepository.findHoaDonByMa(code).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        HoaDonResponse response = HoaDonMapper.toHoaDonResponse(existingBill);
//        Optional<Integer> quantity = serialNumberDaBanRepository.getQuantityByHoaDonId(existingBill.getId());
//        response.setTongSanPham(quantity.orElse(0));
//        if (response.getDiaChi() != null) {
//            String[] diaChiParts = response.getDiaChi().split("\\|");
//            for (int i = 0; i < diaChiParts.length; i++) {
//                diaChiParts[i] = diaChiParts[i].trim();
//            }
//            if (diaChiParts.length == 4) {
//                String diaChiChiTiet = diaChiParts[0];
//                String phuongXa = diaChiParts[1];
//                String quanHuyen = diaChiParts[2];
//                String tinhThanh = diaChiParts[3];
//                response.setDiaChi(diaChiChiTiet);
//                response.setPhuong(phuongXa);
//                response.setHuyen(quanHuyen);
//                response.setTinh(tinhThanh);
//            } else {
//                System.out.println("Chuỗi địa chỉ không có đủ 4 phần.");
//            }
//        }
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

        Meta meta = Meta.builder().page(responses.getNumber()).pageSize(responses.getSize()).pages(responses.getTotalPages()).total(responses.getTotalElements()).build();

        ResultPaginationResponse response = ResultPaginationResponse.builder().meta(meta).result(responses.getContent()).build();
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(String code, String status, CreateLichSuHoaDon request) throws AppException {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
        if (optional.isPresent()) {
            if (optional.get().getTongSanPham() <= 0) {
                throw new AppException(ErrorCode.BILL_WITHOUT_PRODUCT);
            }
            HoaDon bill = optional.get();
            Integer statusHistory = BillUtils.convertBillStatusEnumToInteger(HoaDonStatus.getHoaDonStatusEnumByKey(status));
//            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            billHistoryService.updateStatusBill(request, bill.getMa(), statusHistory);
            if (HoaDonStatus.XAC_NHAN.name().equals(status)) {
                serialNumberService.getSerialSoldInBill(1, bill.getId());
            } else if (HoaDonStatus.CHO_GIAO.name().equals(status)) {

            } else if (HoaDonStatus.DANG_GIAO.name().equals(status)) {
                bill.setNgayGiaoHang(LocalDateTime.now());
            } else if (HoaDonStatus.HOAN_THANH.name().equals(status)) {
                if (bill.getTrangThai() == HoaDonStatus.DON_MOI || bill.getTrangThai() == HoaDonStatus.TREO) {
                    serialNumberService.getSerialSoldInBill(1, bill.getId());
                    throw new RuntimeException("Bạn cần thanh toán ở màn bán hàng");
                }

                bill.setNgayNhanHang(LocalDateTime.now());
                bill.setNgayThanhToan(LocalDateTime.now());
                Optional<HoaDonHinhThucThanhToan> hoaDonHinhThucThanhToan = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(optional.get().getId(), 1);
                if (hoaDonHinhThucThanhToan.isPresent()) {
                    hoaDonHinhThucThanhToan.get().setLoaiThanhToan(0);
                    hoaDonHinhThucThanhToan.get().setTrangThai(0);
//                    hoaDonHinhThucThanhToan.get().setNguoiSua("Nguyễn Tiến Mạnh");
//                    hoaDonHinhThucThanhToan.get().setNguoiTao("Nguyễn Tiến Mạnh");
                    hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan.get());
                }
//                if (bill.getKhachHang() != null) {
//                    updateCustomerRank(bill.getKhachHang().getId());
//                }
            } else if (HoaDonStatus.HUY.name().equals(status)) {
                productRefund(bill);
                // hòan phiếu giảm giá
                optional.get().setTongSanPham(0);
            }
            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(bill));
            if (bill.getEmail() != null && bill.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                sendEmailBill.sendEmailXacNhan(response, "Đơn hàng của bạn đã được : ");
            }
        } else {
            throw new AppException(ErrorCode.BILL_NOT_FOUND);
        }

    }

    @Override
    public void updateStatusCLient(String code, String status, CreateLichSuHoaDonClient request) throws AppException {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
        if (optional.isPresent()) {
            HoaDon bill = optional.get();
            Integer statusHistory = BillUtils.convertBillStatusEnumToInteger(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            billHistoryService.updateStatusBillClient(request, bill.getMa(), statusHistory);
            if (HoaDonStatus.XAC_NHAN.name().equals(status)) {

            } else if (HoaDonStatus.CHO_GIAO.name().equals(status)) {

            } else if (HoaDonStatus.DANG_GIAO.name().equals(status)) {
                bill.setNgayGiaoHang(LocalDateTime.now());
            } else if (HoaDonStatus.HOAN_THANH.name().equals(status)) {
                bill.setNgayNhanHang(LocalDateTime.now());
                bill.setNgayThanhToan(LocalDateTime.now());
                Optional<HoaDonHinhThucThanhToan> hoaDonHinhThucThanhToan = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(optional.get().getId(), 1);
                if (hoaDonHinhThucThanhToan.isPresent()) {
                    hoaDonHinhThucThanhToan.get().setLoaiThanhToan(0);
//                    hoaDonHinhThucThanhToan.get().setNguoiSua("Nguyễn Tiến Mạnh");
//                    hoaDonHinhThucThanhToan.get().setNguoiTao("Nguyễn Tiến Mạnh");
                    hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan.get());
                }
                if (bill.getKhachHang() != null) {
                    updateCustomerRank(bill.getKhachHang().getId());
                }
            } else if (HoaDonStatus.HUY.name().equals(status)) {
                productRefund(bill);
                optional.get().setTongSanPham(0);
            }
            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(bill));
//            sendEmailBill.sendEmailXacNhan(response, "Đơn hàng của bạn đã được : ");
        } else {
            throw new AppException(ErrorCode.BILL_NOT_FOUND);
        }
    }

    public List<String> listHangBill() {
        return billRepository.findAllByTrangThai(HoaDonStatus.TREO).stream().map(HoaDon::getMa).collect(Collectors.toList());
    }

    @Override
    public void changeStatus(String code, String status) throws AppException {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
//        if(optional.isPresent()) {
//            if(optional.get().getTongSanPham()<=0){
//                throw new AppException(ErrorCode.BILL_WITHOUT_PRODUCT);
//            }
//        }
        if (optional.isPresent()) {

            if (optional.get().getTrangThai() == HoaDonStatus.DON_MOI || (optional.get().getTrangThai() == HoaDonStatus.TREO && !status.equals("DON_MOI"))) {
                serialNumberService.getSerialSoldInBill(1, optional.get().getId());
                if (!status.equals("TREO")) {
                    throw new RuntimeException("Bạn cần thanh toán ở màn bán hàng");
                }
            }
            HinhThucThanhToan payment = hinhThucThanhToanRepository.findById(1L).get();
            HoaDon bill = optional.get();
            if (!(HoaDonStatus.TREO.name().equals(status) || HoaDonStatus.DON_MOI.name().equals(status))) {
                if (optional.get().getTongSanPham() <= 0) {
                    throw new AppException(ErrorCode.BILL_WITHOUT_PRODUCT);
                }
            }

            if (HoaDonStatus.DANG_GIAO.name().equals(status)) {
                bill.setNgayGiaoHang(LocalDateTime.now());
            } else if (HoaDonStatus.HOAN_THANH.name().equals(status)) {
                if (optional.get().getTrangThai() == HoaDonStatus.DON_MOI || optional.get().getTrangThai() == HoaDonStatus.TREO) {
                    serialNumberService.getSerialSoldInBill(1, bill.getId());
                }

                bill.setNgayNhanHang(LocalDateTime.now());
                bill.setNgayThanhToan(LocalDateTime.now());
                Optional<HoaDonHinhThucThanhToan> hoaDonHinhThucThanhToan = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(optional.get().getId(), 1);
                if (hoaDonHinhThucThanhToan.isPresent()) {
                    hoaDonHinhThucThanhToan.get().setLoaiThanhToan(0);
                    hoaDonHinhThucThanhToan.get().setTrangThai(0);
//                    hoaDonHinhThucThanhToan.get().setTienNhan(bill.getTongTienPhaiTra().add(bill.getTienShip()));
//                    hoaDonHinhThucThanhToan.get().setNguoiSua("Nguyễn Tiến Mạnh");
//                    hoaDonHinhThucThanhToan.get().setNguoiTao("Nguyễn Tiến Mạnh");
                    hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan.get());
                }
            } else if (HoaDonStatus.XAC_NHAN.name().equals(status)) {
                if (!bill.getTrangThai().getName().equals("CHO_XAC_NHAN")) {
                    serialNumberService.getSerialSoldInBill(1, bill.getId());
                }
                if (bill.getTrangThai().name().equals("CHO_XAC_NHAN")) {
                    System.out.println("Cho xac nhan va cap nhap serrila");
                    List<SerialNumber> serials = serialNumberRepository.findSerialNumbersByDaBanByStatusAndBillId(0, bill.getId());
                    serials.forEach(serialNumber -> serialNumber.setTrangThai(1));
                    serialNumberRepository.saveAll(serials);
                } else {
                    System.out.println("ko vaof : Cho xac nhan va cap nhap serrila");
                }
                List<HoaDonHinhThucThanhToan> list = hoaDonHinhThucThanhToanRepository.findAllByHoaDonMa(code);
                if (list.isEmpty()) {
                    HoaDonHinhThucThanhToan hinhThucThanhToan = new HoaDonHinhThucThanhToan();
                    hinhThucThanhToan.setSoTien(bill.getTongTienPhaiTra().add(bill.getTienShip()));
                    hinhThucThanhToan.setHoaDon(bill);
                    hinhThucThanhToan.setTienNhan((bill.getTongTienPhaiTra().add(bill.getTienShip())));
                    hinhThucThanhToan.setLoaiThanhToan(1);
                    hinhThucThanhToan.setTrangThai(1);
                    hinhThucThanhToan.setHinhThucThanhToan(payment);
                    hoaDonHinhThucThanhToanRepository.save(hinhThucThanhToan);
                }
            }
            bill.setTrangThai(HoaDonStatus.getHoaDonStatusEnumByKey(status));
            billRepository.save(bill);
            if (!status.equals("TREO") && !status.equals("DON_MOI")) {
                Integer statusHistory = BillUtils.convertBillStatusEnumToInteger(HoaDonStatus.getHoaDonStatusEnumByKey(status));
                billHistoryService.updateStatusBill(bill.getMa(), statusHistory);
            }

        }
    }

    @Override
    public ResultPaginationResponse filterHoaDon(Specification<HoaDon> specification, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "ngaySua"));
        Page<HoaDon> billPage = billRepository.findAll(specification, sortedPageable);
        Page<HoaDonResponse> responses = billPage.map(HoaDonMapper::toHoaDonResponse);

        Meta meta = Meta.builder().page(responses.getNumber()).pageSize(responses.getSize()).pages(responses.getTotalPages()).total(responses.getTotalElements()).build();

        return ResultPaginationResponse.builder().meta(meta).result(responses.getContent()).build();
    }

    @Override
    public byte[] export(Specification<HoaDon> specification) throws IOException {
        List<HoaDon> result = billRepository.findAll(specification);
        List<HoaDonResponse> resultResponse = result.stream().map(HoaDonMapper::toHoaDonResponse).toList();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoa Don");
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Mã hóa đơn", "Tên khách hàng", "Số sản phẩm", "Tổng tiền", "Loại hóa đơn", "Ngày tạo"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int rowIdx = 1;
        for (HoaDonResponse response : resultResponse) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(response.getMa());
            row.createCell(1).setCellValue(response.getTenKhachHang());
            row.createCell(2).setCellValue(response.getTongSanPham());
            row.createCell(3).setCellValue(response.getTongTienBanDau().toString());
            row.createCell(4).setCellValue(response.getLoaiHoaDon() == 0 ? "Tại quầy" : "Ship");
            row.createCell(5).setCellValue(response.getNgayTao());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }


    @Override
    public void deleteBillByCode(String code) {
        Optional<HoaDon> optional = billRepository.findHoaDonByMa(code);
        if (optional.isPresent()) {
            optional.get().setTrangThai(HoaDonStatus.XOA);
            billRepository.save(optional.get());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public HoaDonResponse addCustomerToBill(Long customerId, String billCode) throws AppException {
        Optional<KhachHang> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            Optional<HoaDon> bill = billRepository.findHoaDonByMa(billCode);
            if (bill.isPresent()) {
                KhachHang existingCustomer = customer.get();
                HoaDon existingBill = bill.get();
                existingBill.setKhachHang(existingCustomer);
                DiaChi_Response addressResponse = diaChiService.getDiaChiDefauldOfIdKhachHang(existingCustomer.getId());
                String address = addressResponse.getDiaChiNhanHang() + "|" + addressResponse.getIdPhuongXa() + "|" + addressResponse.getIdQuanHuyen() + "|" + addressResponse.getIdTinhThanhPho();
                existingBill.setDiaChi(address);
                existingBill.setEmail(addressResponse.getEmailNguoiNhan());
                existingBill.setSdt(addressResponse.getSdtNguoiNhan());
                // tao lich su thanh doi
                // check thay đổi khách hàng
                Long couponIdExiting = existingBill.getPhieuGiamGia() != null ? existingBill.getPhieuGiamGia().getId() : null;
                Optional<PhieuGiamGia> optional = couponRepository.getVoucherByTotalAmountCustomerAndCoupon(existingBill.getTongTienBanDau(), customerId, couponIdExiting);
                if (!optional.isPresent()) {
                    existingBill.setPhieuGiamGia(null);
                    existingBill.setTongTienPhaiTra(existingBill.getTongTienBanDau());
                } else {
                    // nếu thay đổi kh
                    Optional<BigDecimal> totalAmount = couponRepository.findDiscountValue(existingBill.getTongTienPhaiTra(), optional.get().getId());
                    if (totalAmount.isPresent()) {
                        existingBill.setTongTienPhaiTra(existingBill.getTongTienBanDau().subtract(totalAmount.get()));
                    }
                }
                // giảm hạng
//                BigDecimal rank = RankCustomer.getValueByRank(existingCustomer.getHangKhachHang());
//                existingBill.setTienGiamHangKhachHang(rank);
//                BigDecimal newTongTienPhaiTra = existingBill.getTongTienPhaiTra().subtract(rank);
//                if (newTongTienPhaiTra.compareTo(BigDecimal.ZERO) < 0) {
//                    newTongTienPhaiTra = BigDecimal.ZERO;
//                    existingBill.setTongTienPhaiTra(newTongTienPhaiTra);
//                    existingBill.setTienGiamHangKhachHang(BigDecimal.ZERO);
//                } else {
//                    existingBill.setTongTienPhaiTra(newTongTienPhaiTra);
//                }

                HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(bill.get()));
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
                    }
                }
                return response;
            } else {
                throw new AppException(ErrorCode.BILL_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public HoaDonResponse addCouponToBill(Long couponId, String billCode) throws AppException {
        HoaDon existingBill = billRepository.findHoaDonByMa(billCode).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        PhieuGiamGia coupon = couponRepository.findById(couponId).orElseThrow(() -> new AppException(ErrorCode.COUPONS_NOT_FOUND));
        BigDecimal moneyReduced = calculateDiscount(existingBill, coupon);
//        calculateDiscount(existingBill, coupon);
        updateTotalMoney(existingBill, moneyReduced);
        existingBill.setPhieuGiamGia(coupon);
        return HoaDonMapper.toHoaDonResponse(billRepository.save(existingBill));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public HoaDonResponse addCouponToBillByCode(String couponCode, String billCode) throws AppException {
        if (couponCode.trim().isEmpty()) {
            throw new AppException(ErrorCode.COUPON_CODE_NOT_BLANK);
        }
        HoaDon existingBill = billRepository.findHoaDonByMa(billCode).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        PhieuGiamGia coupon = couponRepository.findByMa(couponCode.trim().toUpperCase()).orElseThrow(() -> new AppException(ErrorCode.COUPONS_NOT_FOUND));
        if (existingBill.getPhieuGiamGia() != null) {
            if (Objects.equals(existingBill.getPhieuGiamGia().getId(), coupon.getId())) {
                throw new AppException(ErrorCode.COUPON_ALREADY_EXISTS_IN_BILL);
            }
        }
        boolean isCustomerNull = existingBill.getKhachHang() == null;
        Optional<PhieuGiamGia> optional = isCustomerNull ? couponRepository.getByTotalAmountAndCouponCode(existingBill.getTongTienBanDau(), coupon.getMa()) : couponRepository.getAllByTotalAmountAndCustomerAndCouponCode(existingBill.getTongTienBanDau(), existingBill.getKhachHang().getId(), coupon.getMa());

        if (!optional.isPresent()) {
            throw new AppException(ErrorCode.COUPON_DOES_NOT_APPLY);
        }

        BigDecimal moneyReduced = calculateDiscount(existingBill, coupon);
        updateTotalMoney(existingBill, moneyReduced);
        existingBill.setPhieuGiamGia(coupon);
        return HoaDonMapper.toHoaDonResponse(billRepository.save(existingBill));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String payCounter(String billCode, UpdateHoaDonRequest request) throws AppException {
        HoaDon bill = billRepository.findHoaDonByMa(billCode.trim()).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        Optional<Integer> quantityInBill = serialNumberDaBanRepository.getQuantityByHoaDonId(bill.getId());
        if (quantityInBill.isEmpty() || quantityInBill.get() == 0) {
            throw new AppException(ErrorCode.BILL_WITHOUT_PRODUCT);
        }
        //
        serialNumberService.getSerialSoldInBill(1, bill.getId());
        List<SerialNumber> serialNumbersInBill = serialNumberRepository.getListSerialInBill(bill.getId());
        serialNumbersInBill.forEach(serialNumber -> serialNumber.setTrangThai(1));
        serialNumberRepository.saveAll(serialNumbersInBill);
        //
        bill.setTienShip(request.getTienShip());
        if (request.getThanhToanSau() == 1) {
            Optional<HoaDonHinhThucThanhToan> optional = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(bill.getId(), 0);
            if (optional.isPresent()) {
                throw new AppException(ErrorCode.THANH_TOAN_PHAI_TOAN_PHAM);
            }
        }
        // trừ phiếu giảm giá
//        if (bill.getPhieuGiamGia() != null) {
//            Optional<PhieuGiamGia> couponOptional = couponRepository.findById(bill.getPhieuGiamGia().getId());
//            if (couponOptional.isPresent()) {
//                PhieuGiamGia coupon = couponOptional.get();
//                Integer quantity = coupon.getSoLuong() - 1;
//                coupon.setSoLuong(quantity);
//                PhieuGiamGia exitingCoupon = couponRepository.save(coupon);
//                if (exitingCoupon.getSoLuong() == 0) {
//                    exitingCoupon.setTrangThai(3);
//                    couponRepository.save(exitingCoupon);
//                }
//                if (coupon.getPhamViApDung() == 2) {
//                    Optional<KhachHangPhieuGiamGia> optional = khachHangPhieuGiamGiaRepository.findKhachHangPhieuGiamGiaByPhieuGiamGiaIdAndKhachHangId(coupon.getId(), bill.getKhachHang().getId());
//                    optional.get().setTrangThai(1);// 1 đã dùng
//                    khachHangPhieuGiamGiaRepository.save(optional.get());
//                }
//            }
//        }
        updateCoupons(bill.getPhieuGiamGia(), bill);
//        lưu theo trạng thái
        LichSuHoaDon billHistory = new LichSuHoaDon();
        HinhThucThanhToan tienMat = hinhThucThanhToanRepository.findById(1L).get();
        HinhThucThanhToan chuyenKhoan = hinhThucThanhToanRepository.findById(2L).get();

        if (request.getLoaiHoaDon() == 0) {
            // toonhr tienf đã trả
            BigDecimal total = hoaDonHinhThucThanhToanSerive.getAllByBillCode(bill.getMa()).stream().filter(item -> item.getLoaiThanhToan() == 0).map(HoaDonHinhThucThanhToanResponse::getSoTien).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (total.compareTo(bill.getTongTienPhaiTra()) < 0) {
                throw new AppException(ErrorCode.TIEN_KO_DU);
            }

            bill.setTrangThai(HoaDonStatus.HOAN_THANH);
            bill.setNgayThanhToan(LocalDateTime.now());
            bill.setLoaiHoaDon(request.getLoaiHoaDon());

            HoaDonResponse response = HoaDonMapper.toHoaDonResponse(billRepository.save(bill));
            billHistory.setHoaDon(bill);
            billHistory.setTrangThai(6);
//            billHistory.setNguoiSua("Nguyễn Tiến Mạnh");
//            billHistory.setNguoiTao("Nguyễn Tiến Mạnh");
            billHistory.setGhiChuChoCuaHang("Thanh toán thành công");
            billHistory.setGhiChuChoKhachHang("Thanh toán thành công");
//            NhanVien nhanVien = employeeRepository.findById(1L).get()
            Optional<String> emailOption = JwtUtil.getCurrentUserLogin();
            if (emailOption.isPresent() && !emailOption.get().equals("anonymousUser")) {
                NhanVien existingEmployee = employeeRepository.findByEmail(emailOption.get());
//                bill.setNhanVien(existingEmployee);
                billHistory.setNhanVien(existingEmployee);
            }
            sendEmailBill.sendEmailXacNhan(response, "Cảm ơn ban đã mua hàng");
//            billHistory.setNhanVien(nhanVien);

//            if (bill.getKhachHang() != null) {
////                updateCustomerRank(bill.getKhachHang().getId());
//            }
        } else if (request.getLoaiHoaDon() == 1) {
            // lưu
            if (request.getThanhToanSau() == 1) {
                HoaDonHinhThucThanhToan hoaDonHinhThucThanhToan = new HoaDonHinhThucThanhToan();
                hoaDonHinhThucThanhToan.setSoTien(bill.getTienShip().add(bill.getTongTienPhaiTra()));
                hoaDonHinhThucThanhToan.setTienNhan(bill.getTienShip().add(bill.getTongTienPhaiTra()));
                hoaDonHinhThucThanhToan.setHoaDon(bill);
//                hoaDonHinhThucThanhToan.setNguoiSua("Nguyễn Tiến Mạnh");
//                hoaDonHinhThucThanhToan.setNguoiTao("Nguyễn Tiến Mạnh");
                hoaDonHinhThucThanhToan.setLoaiThanhToan(request.getLoaiHoaDon());
                hoaDonHinhThucThanhToan.setHinhThucThanhToan(tienMat);
                hoaDonHinhThucThanhToan.setTrangThai(1);
                hoaDonHinhThucThanhToanRepository.save(hoaDonHinhThucThanhToan);
            } else {

            }

            bill.setTrangThai(HoaDonStatus.XAC_NHAN);
            bill.setLoaiHoaDon(request.getLoaiHoaDon());
            bill.setThanhToanSau(request.getThanhToanSau());
            bill.setTenKhachHang(request.getTen());
            bill.setSdt(request.getSdt());
            bill.setEmail(request.getEmail());
            bill.setTienShip(request.getTienShip());
            String diaChi = (request.getDiaChi() != null && !request.getDiaChi().isEmpty() ? request.getDiaChi() + " , " : "") + request.getTenPhuong() + " , " + request.getTenHuyen() + " , " + request.getTenTinh() + " | " + request.getPhuong() + " | " + request.getHuyen() + " | " + request.getTinh();
            bill.setDiaChi(diaChi);
            bill.setGhiChu(request.getGhiChu());
            billRepository.save(bill);
            billHistory.setHoaDon(bill);
            billHistory.setTrangThai(9);
//            billHistory.setNguoiSua("Nguyễn Tiến Mạnh");
//            billHistory.setNguoiTao("Nguyễn Tiến Mạnh");
            billHistory.setGhiChuChoCuaHang("Hóa đơn chuyển sang chờ xác nhận");
            billHistory.setGhiChuChoKhachHang("Chờ xác nhận");
//            NhanVien nhanVien = employeeRepository.findById(1L).get();
            Optional<String> emailOption = JwtUtil.getCurrentUserLogin();
            if (emailOption.isPresent() && !emailOption.get().equals("anonymousUser")) {
                NhanVien existingEmployee = employeeRepository.findByEmail(emailOption.get());
//                newBill.setNhanVien(existingEmployee);
                billHistory.setNhanVien(existingEmployee);
            }

        }
        billHistoryRepository.save(billHistory);
        return bill.getMa();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAddressInBill(String billCode, UpdateDiaChiHoaDonRequest request) throws AppException {
        HoaDon bill = billRepository.findHoaDonByMa(billCode.trim()).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
// check trạng thái
        String diaChi = request.getDiaChi().trim() + "," + request.getTenPhuong() + "," + request.getTenHuyen() + "," + request.getTenTinh() + " | " + request.getPhuong() + " | " + request.getHuyen() + " | " + request.getTinh();

        if (bill.getTrangThai() != HoaDonStatus.DON_MOI && bill.getTrangThai() != HoaDonStatus.TREO) {
            BillUtils.addMoney(bill.getMa(), bill.getTienShip());
            BigDecimal giaShipGoc = BillUtils.getMoney(bill.getMa());
            BillUtils.printAll();
            boolean check = false;
            if (giaShipGoc.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("Vào 0");
                check = true;
            }
//        BigDecimal newMoneyShip = request.getTienShip().subtract(bill.getTienShip());
            BigDecimal newMoneyShip = request.getTienShip().subtract(giaShipGoc);
            System.out.println("Giá ship goc : " + giaShipGoc);
            System.out.println("Chênh lệnh : " + newMoneyShip);

            if (newMoneyShip.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Vào 1");
                Optional<HoaDonHinhThucThanhToan> optionalTraSau = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(bill.getId(), 1);
                Optional<HoaDonHinhThucThanhToan> optionalTraTruoc = hoaDonHinhThucThanhToanRepository.findByHoaDonIdAndLoaiThanhToan(bill.getId(), 0);
                HinhThucThanhToan payment = hinhThucThanhToanRepository.findById(1L).get();
                if (optionalTraSau.isPresent()) {
                    System.out.println("Vào 2");
                    HoaDonHinhThucThanhToan hinhThucThanhToan = optionalTraSau.get();
                    hinhThucThanhToan.setSoTien(hinhThucThanhToan.getSoTien().add(request.getTienShip()).subtract(giaShipGoc));
                    hinhThucThanhToan.setTienNhan(hinhThucThanhToan.getTienNhan().add(request.getTienShip()).subtract(giaShipGoc));
                    hinhThucThanhToan.setTrangThai(1);
                    hinhThucThanhToan.setLoaiThanhToan(1);
                    hinhThucThanhToan.setHinhThucThanhToan(payment);
                    hoaDonHinhThucThanhToanRepository.save(hinhThucThanhToan);
                } else {
                    System.out.println("Vào 3");
                    HoaDonHinhThucThanhToan hinhThucThanhToan = new HoaDonHinhThucThanhToan();
                    hinhThucThanhToan.setSoTien(newMoneyShip);
                    hinhThucThanhToan.setHoaDon(bill);
                    hinhThucThanhToan.setTienNhan((newMoneyShip));
                    hinhThucThanhToan.setLoaiThanhToan(1);
                    hinhThucThanhToan.setTrangThai(1);
                    hinhThucThanhToan.setHinhThucThanhToan(payment);
                    hoaDonHinhThucThanhToanRepository.save(hinhThucThanhToan);
                }
            }
        }


        bill.setDiaChi(diaChi);
        bill.setTienShip(request.getTienShip());
        bill.setTenKhachHang(request.getTen());
        bill.setGhiChu(request.getGhiChu());
        bill.setSdt(request.getSdt());
        bill.setEmail(request.getEmail());
        bill.setLoaiHoaDon(1);
        billRepository.save(bill);
        if (!bill.getTrangThai().name().equals("DON_MOI") && !bill.getTrangThai().name().equals("TREO")) {
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(bill);
            lichSuHoaDon.setTrangThai(12);//cap nhap don hang
            lichSuHoaDon.setKhachHang(bill.getKhachHang());
            lichSuHoaDon.setNhanVien(bill.getNhanVien());
            lichSuHoaDon.setGhiChuChoCuaHang("Cập nhập địa chỉ");
//            lichSuHoaDon.setNguoiTao("Mạnh cập nhập");
//            lichSuHoaDon.setNguoiSua("Mạnh cập nhập");
            billHistoryRepository.save(lichSuHoaDon);
        }
    }

    @Override
    public Long countBillByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepository.countBillByDate(startDate, endDate);
    }

    @Override
    public BigDecimal sumBillByDate(Long startDate, Long endDate) {
        return null;
    }

    @Override
    public void updateCustomerRank(Long idKhachHang) {
        {
            Optional<BigDecimal> totalSpentOpt = billRepository.tongTienDaChiCuaKhachHang(idKhachHang);
            if (totalSpentOpt.isPresent()) {
                BigDecimal totalSpent = totalSpentOpt.get();
                Integer rank = RankCustomer.getRankByValue(totalSpent);
                if (rank != -1) {
                    Optional<KhachHang> khachHang = customerRepository.findById(idKhachHang);
                    if (khachHang.isPresent()) {
                        khachHang.get().setHangKhachHang(rank);
                        customerRepository.save(khachHang.get());
                    }

                }
            }
        }
    }

    @Override
    public byte[] getInvoicePdf(String billCode) throws AppException, IOException, WriterException {
        Context context = new Context();
        HoaDonResponse bill = this.getBillByCode(billCode);
        BillPdfResponse billPdfResponse = HoaDonMapper.toBillPdfResponse(bill);
        List<SerialNumberDaBanResponse> serials = serialNumberDaBanService.getSerialNumberDaBanPage(billCode);
        List<SerialNumberDaBanPdfResponse> serialsPdf = serials.stream().map(SerialNumberSoldMapper::toSerialNumberDaBanPdfResponse).toList();
        List<HoaDonHinhThucThanhToan> paymentHistory0 = hoaDonHinhThucThanhToanRepository.findAllByHoaDonIdAndLoaiThanhToan(bill.getId(), 0);
        List<HoaDonHinhThucThanhToanPdfReponse> paymentHistoryPdf0 = paymentHistory0.stream().map(HoaDonHinhThucThanhToanMapper::toHoaDonHinhThucThanhToanPdfReponse).toList();
        BigDecimal khachDaThanhToan = paymentHistory0.stream().map(HoaDonHinhThucThanhToan::getSoTien).filter(t -> t != null).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        // max + min
        List<HoaDonHinhThucThanhToan> paymentHistory = hoaDonHinhThucThanhToanRepository.findAllByHoaDonIdAndLoaiThanhToan(bill.getId(), 1);
        List<HoaDonHinhThucThanhToanPdfReponse> paymentHistoryPdf = paymentHistory.stream().map(HoaDonHinhThucThanhToanMapper::toHoaDonHinhThucThanhToanPdfReponse).toList();

        context.setVariable("bill", billPdfResponse);
        context.setVariable("serials", serialsPdf);
        context.setVariable("paymentHistory", paymentHistoryPdf);
        context.setVariable("paymentHistory0", paymentHistoryPdf0);
        context.setVariable("khachDaThanhToan", BillUtils.convertMoney(khachDaThanhToan));

        System.out.println("Processing template with invoice: " + billCode);

        String processedHtml = templateEngine.process("templatesBill", context);
//        OutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
////        String fontPath = getClass().getResource("/fonts/Roboto-Regular.ttf").getPath();
////        renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        renderer.setDocumentFromString(processedHtml);
//        renderer.layout();
//        renderer.createPDF(outputStream);

//        return ((ByteArrayOutputStream) outputStream).toByteArray();
        return htmlToPdf(processedHtml, billCode);
    }

    public byte[] htmlToPdf(String processedHtml, String code) {

        String downloadPath = System.getProperty("user.home") + "/Downloads";

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream)) {

            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);

            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            return pdfBytes;
//            Files.copy(new ByteArrayInputStream(pdfBytes), Paths.get(downloadPath, code + ".pdf"), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            // Xử lý ngoại lệ khi có lỗi I/O
            ex.printStackTrace();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createBillClient(CreateHoaDonClientRequest request, HttpServletRequest httpServletRequest) throws AppException {
        Integer totalQuantity = request.getGioHangChiTiet().stream().mapToInt(GioHangChiTietRequest::getSoLuong).sum();
        if (request.getPhuongThucThanhToan() == 1) {
            if (totalQuantity > 10) {
                throw new AppException(ErrorCode.TOTAL_PRODUCT_IN_BILL_MAX_10);
            }
        }
        HoaDon bill = new HoaDon();
        bill.setMa(GenerateCode.generateHoaDon());
        // sp
        // tiền
        bill.setTongTienBanDau(request.getTongTienBanDau());
        bill.setTongTienPhaiTra(request.getTongTienPhaiTra().subtract(request.getTienShip()));
        //
        bill.setLoaiHoaDon(1);
        bill.setThanhToanSau(request.getThanhToanSau());
        bill.setTenKhachHang(request.getTenKhachHang());
        bill.setSdt(request.getSdt());
        bill.setEmail(request.getEmail());
        bill.setTienShip(request.getTienShip());
        String diaChi = request.getDiaChi() + " | " + request.getIdPhuongXa() + " | " + request.getIdQuanHuyen() + " | " + request.getIdTinhThanh();
        bill.setDiaChi(diaChi);
        bill.setGhiChu(request.getGhiChu());

        // pgg
        if (request.getMaPGG() != null && !request.getMaPGG().isEmpty()) {
            Optional<PhieuGiamGia> optional = couponRepository.findByMa(request.getMaPGG().trim());
            if (optional.isPresent()) {
                bill.setPhieuGiamGia(optional.get());
                updateCoupons(optional.get(), bill);
            } else {
                throw new AppException(ErrorCode.COUPONS_NOT_FOUND);
            }

        }

        // thanh toán , ls tt
        //
        HinhThucThanhToan payment = hinhThucThanhToanRepository.findById(request.getPhuongThucThanhToan()).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        if (request.getPhuongThucThanhToan() == 1) {
            bill.setTrangThai(HoaDonStatus.CHO_XAC_NHAN);
            HoaDonHinhThucThanhToan paymentHistory = new HoaDonHinhThucThanhToan();
            paymentHistory.setSoTien(request.getTongTienPhaiTra());
            paymentHistory.setTienNhan(BigDecimal.ZERO);
            paymentHistory.setHoaDon(bill);
            paymentHistory.setTrangThai(1);
            paymentHistory.setLoaiThanhToan(request.getThanhToanSau());
            paymentHistory.setHinhThucThanhToan(payment);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory);
            // tạo lịch sử hóa đơn
        }

        if (request.getPhuongThucThanhToan() == 2) {
            createPaymentHistory(request, bill);
        }
        billRepository.save(bill);
        LichSuHoaDon billHistory = new LichSuHoaDon();
        if (bill.getTrangThai().name().equals("CHO_THANH_TOAN")) {
            billHistory.setTrangThai(1);
        } else {
            billHistory.setTrangThai(2);
        }
        billHistory.setGhiChuChoKhachHang(request.getGhiChu());
        billHistory.setKhachHang(bill.getKhachHang());
        billHistory.setHoaDon(bill);
        billHistoryRepository.save(billHistory);

        // giỏ hàng -> serial number đã bán
        Integer totalProduct = 0;
        for (GioHangChiTietRequest cartDetailRequest : request.getGioHangChiTiet()) {
            Optional<SanPhamChiTiet> optional = sanPhamChiTietRepository.findById(cartDetailRequest.getIdSPCT());
            if (!optional.isPresent()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
            }
            if (optional.get().getTrangThai() == 0) {
                throw new RuntimeException("Sản phẩm " + optional.get().getSanPham().getTen() + " ngừng bán");

            }
            List<SerialNumber> listSerialNumber = serialNumberRepository.findBySanPhamChiTietIdAndTrangThaiWithLimit(cartDetailRequest.getIdSPCT(), cartDetailRequest.getSoLuong());

            if (listSerialNumber.size() < cartDetailRequest.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + optional.get().getSanPham().getTen() + " không đủ . Sản phẩm tồn kho : " + listSerialNumber.size());
            }
            for (SerialNumber serialNumber : listSerialNumber) {
                SerialNumberDaBan serialNumberDaBanSold = SerialNumberDaBan.builder().serialNumber(serialNumber).hoaDon(bill).giaBan(cartDetailRequest.getGia()).build();
                serialNumberDaBanRepository.save(serialNumberDaBanSold);
//                serialNumber.setTrangThai(1);
                serialNumberRepository.save(serialNumber);
                totalProduct++;
            }
//            totalProduct+=totalProduct+cartDetailRequest.getSoLuong();
            // up lại tt sp
            Integer quantityProductIsActive = serialNumberRepository.getQuantitySerialIsActive(cartDetailRequest.getIdSPCT());
            if (quantityProductIsActive != null && quantityProductIsActive == 0) {
                optional.get().setTrangThai(0);
                sanPhamChiTietRepository.save(optional.get());
            }
        }
        bill.setTongSanPham(totalProduct);
        billRepository.save(bill);
        // xóa giỏ hàng
        GioHang cart = new GioHang();
        if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
            Optional<GioHang> optional = gioHangRepository.findBySessionId(request.getSessionId());
            if (optional.isPresent()) {
                cart = optional.get();
            } else {
                throw new RuntimeException("SessionId chuyển vào sai ko lấy được giỏ hàng");
            }
        } else {
            throw new RuntimeException("Cần chuyển SessionId hoặc  Id khách hàng ");
        }
        for (GioHangChiTietRequest cartDetailRequest : request.getGioHangChiTiet()) {
            Optional<GioHangChiTiet> optional = gioHangChiTietRepository.getCartDetailByCartIdAndProductDetailId(cart.getId(), cartDetailRequest.getIdSPCT());
            if (optional.isPresent()) {
                gioHangChiTietService.deleteCartDetail(optional.get().getId());
            }
        }

//        return HoaDonMapper.toHoaDonResponse(bill);
        if (request.getPhuongThucThanhToan() == 1) {
            return bill.getMa() + "|" + bill.getSdt();
        } else {
            return hinhThucThanhToanService.payWithVNPAYOnline2(request, bill, httpServletRequest);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createBillClientAccount(CreateHoaDonClientAccountRequest request, HttpServletRequest httpServletRequest) throws AppException {
        Integer totalQuantity = request.getGioHangChiTiet().stream().mapToInt(GioHangChiTietRequest::getSoLuong).sum();
        if (request.getPhuongThucThanhToan() == 1) {
            if (totalQuantity > 10) {
                throw new AppException(ErrorCode.TOTAL_PRODUCT_IN_BILL_MAX_10);
            }
        }
        HoaDon bill = new HoaDon();
        bill.setMa(GenerateCode.generateHoaDon());

        // sp
        // tiền
        bill.setTongTienBanDau(request.getTongTienBanDau());
        bill.setTongTienPhaiTra(request.getTongTienPhaiTra().subtract(request.getTienShip()));
        //
        bill.setLoaiHoaDon(1);
        bill.setThanhToanSau(request.getThanhToanSau());

        bill.setTienShip(request.getTienShip());
        // goi
        bill.setTenKhachHang(request.getTenKhachHang());
        bill.setSdt(request.getSdt());
        bill.setEmail(request.getEmail());
        String diaChi = request.getDiaChi() + " | " + request.getIdPhuongXa() + " | " + request.getIdQuanHuyen() + " | " + request.getIdTinhThanh();
        bill.setDiaChi(diaChi);
        bill.setGhiChu(request.getGhiChu());

        // pgg
        if (request.getMaPGG() != null && !request.getMaPGG().isEmpty()) {
            Optional<PhieuGiamGia> optional = couponRepository.findByMa(request.getMaPGG().trim());
            if (optional.isPresent()) {
                bill.setPhieuGiamGia(optional.get());
            } else {
                throw new AppException(ErrorCode.COUPONS_NOT_FOUND);
            }
        }
        // khách hàng
        if (request.getIdKhacHang() != null) {
            Optional<KhachHang> optional = customerRepository.findById(request.getIdKhacHang());
            if (optional.isPresent()) {
                bill.setKhachHang(optional.get());
//                bill.setTienGiamHangKhachHang(request.getGiamHangKhachHang());
                if (bill.getPhieuGiamGia() != null) {
                    updateCoupons(bill.getPhieuGiamGia(), bill);
                }
            } else {
                throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
            }
        }
        // thanh toán , ls tt
        //
        HinhThucThanhToan payment = hinhThucThanhToanRepository.findById(request.getPhuongThucThanhToan()).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        if (request.getPhuongThucThanhToan() == 1) {
            bill.setTrangThai(HoaDonStatus.CHO_XAC_NHAN);
            HoaDonHinhThucThanhToan paymentHistory = new HoaDonHinhThucThanhToan();
            paymentHistory.setSoTien(request.getTongTienPhaiTra());
            paymentHistory.setTienNhan(request.getTongTienPhaiTra());
            paymentHistory.setHoaDon(bill);
//            paymentHistory.setNguoiTao("Nguyễn Tiến Mạnh");
//            paymentHistory.setNguoiSua("Nguyễn Tiến Mạnh");
            paymentHistory.setLoaiThanhToan(request.getThanhToanSau());
            paymentHistory.setTrangThai(1);
            paymentHistory.setHinhThucThanhToan(payment);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory);
            // tạo lịch sử hóa đơn
        }
        if (request.getPhuongThucThanhToan() == 2) {
            createPaymentHistoryAccount(request, bill);
        }

        billRepository.save(bill);
        LichSuHoaDon billHistory = new LichSuHoaDon();
        if (bill.getTrangThai().name().equals("CHO_THANH_TOAN")) {
            billHistory.setTrangThai(1);
        } else {
            billHistory.setTrangThai(2);
        }
        billHistory.setGhiChuChoKhachHang(request.getGhiChu());
        billHistory.setKhachHang(bill.getKhachHang());
        billHistory.setHoaDon(bill);
        billHistoryRepository.save(billHistory);

        // giỏ hàng -> serial number đã bán
        Integer totalProduct = 0;
        for (GioHangChiTietRequest cartDetailRequest : request.getGioHangChiTiet()) {
            Optional<SanPhamChiTiet> optional = sanPhamChiTietRepository.findById(cartDetailRequest.getIdSPCT());
            if (!optional.isPresent()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
            }
            if (optional.get().getTrangThai() == 0) {
                throw new RuntimeException("Sản phẩm " + optional.get().getSanPham().getTen() + " ngừng bán");

            }
            List<SerialNumber> listSerialNumber = serialNumberRepository.findBySanPhamChiTietIdAndTrangThaiWithLimit(cartDetailRequest.getIdSPCT(), cartDetailRequest.getSoLuong());

            if (listSerialNumber.size() < cartDetailRequest.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + optional.get().getSanPham().getTen() + " không đủ . Sản phẩm tồn kho : " + listSerialNumber.size());
            }
            for (SerialNumber serialNumber : listSerialNumber) {
                SerialNumberDaBan serialNumberDaBanSold = SerialNumberDaBan.builder().serialNumber(serialNumber).hoaDon(bill).giaBan(cartDetailRequest.getGia()).build();
                serialNumberDaBanRepository.save(serialNumberDaBanSold);
//                serialNumber.setTrangThai(1);
                serialNumberRepository.save(serialNumber);
                totalProduct++;
            }
//            totalProduct+=totalProduct+cartDetailRequest.getSoLuong();
            // up lại tt sp
            Integer quantityProductIsActive = serialNumberRepository.getQuantitySerialIsActive(cartDetailRequest.getIdSPCT());
            if (quantityProductIsActive != null && quantityProductIsActive == 0) {
                optional.get().setTrangThai(0);
                sanPhamChiTietRepository.save(optional.get());
            }
        }
        bill.setTongSanPham(totalProduct);
        billRepository.save(bill);
        // xóa giỏ hàng
        GioHang cart = new GioHang();
        if (request.getIdKhacHang() != null) {
            Optional<GioHang> optional = gioHangRepository.findByKhachHangId(request.getIdKhacHang());
            if (optional.isPresent()) {
                cart = optional.get();
            } else {
                throw new RuntimeException("Id khách hàng chuyển vào sai ko lấy được giỏ hàng");
            }
        } else {
            throw new RuntimeException("Cần chuyển SessionId hoặc  Id khách hàng ");
        }
        for (GioHangChiTietRequest cartDetailRequest : request.getGioHangChiTiet()) {
            Optional<GioHangChiTiet> optional = gioHangChiTietRepository.getCartDetailByCartIdAndProductDetailId(cart.getId(), cartDetailRequest.getIdSPCT());
            if (optional.isPresent()) {
                gioHangChiTietService.deleteCartDetail(optional.get().getId());
            }
        }
        if (request.getPhuongThucThanhToan() == 1) {
            return bill.getMa() + "|" + bill.getSdt();
        } else {
            return hinhThucThanhToanService.payWithVNPAYAccountOnline(request, bill, httpServletRequest);
        }
    }

    private void createPaymentHistoryAccount(CreateHoaDonClientAccountRequest request, HoaDon bill) throws AppException {
        bill.setTrangThai(HoaDonStatus.CHO_XAC_NHAN);
        HinhThucThanhToan paymentCK = hinhThucThanhToanRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        HinhThucThanhToan paymentTM = hinhThucThanhToanRepository.findById(1L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        BigDecimal threshold = new BigDecimal("100000000");
        if (request.getTongTienPhaiTra().compareTo(threshold) > 0) {
            System.out.println("Lớn 100 ");
            request.setTienChuyenKhoan(threshold);
            BigDecimal soTienConNo = request.getTongTienPhaiTra().subtract(threshold);
            System.out.println("Lớn 100tr " + soTienConNo);
            HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
            paymentHistory11.setSoTien(request.getTongTienPhaiTra());
            paymentHistory11.setTienNhan(threshold);
            paymentHistory11.setHoaDon(bill);
            paymentHistory11.setNguoiTao("CK");
            paymentHistory11.setNguoiSua("CK");
            paymentHistory11.setLoaiThanhToan(0);
            paymentHistory11.setTrangThai(1);
            paymentHistory11.setHinhThucThanhToan(paymentCK);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
            // thanh toán sau
            HoaDonHinhThucThanhToan paymentHistory2 = new HoaDonHinhThucThanhToan();
            paymentHistory2.setSoTien(request.getTongTienPhaiTra());
            paymentHistory2.setTienNhan(soTienConNo);
            paymentHistory2.setHoaDon(bill);
            paymentHistory2.setNguoiTao("Nợ");
            paymentHistory2.setNguoiSua("Nợ");
            paymentHistory2.setLoaiThanhToan(1);
            paymentHistory11.setTrangThai(1);
            paymentHistory2.setHinhThucThanhToan(paymentTM);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory2);
        } else {
            if (request.getTongTienPhaiTra().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
                System.out.println("Dưới 100 và  lẻ");
                BigDecimal tongTienPhaiTra = request.getTongTienPhaiTra();
                BigDecimal phanNguyen = tongTienPhaiTra.setScale(0, BigDecimal.ROUND_DOWN);
                BigDecimal phanThapPhan = tongTienPhaiTra.subtract(phanNguyen);
                request.setTienChuyenKhoan(phanNguyen);
                //
                HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
                paymentHistory11.setSoTien(request.getTongTienPhaiTra());
                paymentHistory11.setTienNhan(phanNguyen);
                paymentHistory11.setHoaDon(bill);
                paymentHistory11.setNguoiTao("CK");
                paymentHistory11.setNguoiSua("CK");
                paymentHistory11.setLoaiThanhToan(0);
                paymentHistory11.setTrangThai(1);
                paymentHistory11.setHinhThucThanhToan(paymentCK);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
                // phần lẻ
                System.out.println("Phần thập phân: " + phanThapPhan);
                HoaDonHinhThucThanhToan paymentHistory2 = new HoaDonHinhThucThanhToan();
                paymentHistory2.setSoTien(request.getTongTienPhaiTra());
                paymentHistory2.setTienNhan(phanThapPhan);
                paymentHistory2.setHoaDon(bill);
                paymentHistory2.setNguoiTao("Nợ");
                paymentHistory2.setNguoiSua("Nợ");
                paymentHistory2.setLoaiThanhToan(1);
                paymentHistory11.setTrangThai(0);
                paymentHistory2.setHinhThucThanhToan(paymentTM);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory2);
            } else {
                System.out.println("Dưới 100tr và ko lẻ");
                request.setTienChuyenKhoan(request.getTongTienPhaiTra());
                HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
                paymentHistory11.setSoTien(request.getTongTienPhaiTra());
                paymentHistory11.setTienNhan(request.getTongTienPhaiTra());
                paymentHistory11.setHoaDon(bill);
                paymentHistory11.setNguoiTao("CK");
                paymentHistory11.setNguoiSua("CK");
                paymentHistory11.setLoaiThanhToan(0);
                paymentHistory11.setTrangThai(1);
                HinhThucThanhToan payment11 = hinhThucThanhToanRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
                paymentHistory11.setHinhThucThanhToan(payment11);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
            }
        }
    }

    private void createPaymentHistory(CreateHoaDonClientRequest request, HoaDon bill) throws AppException {
        bill.setTrangThai(HoaDonStatus.CHO_THANH_TOAN);
        HinhThucThanhToan paymentCK = hinhThucThanhToanRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        HinhThucThanhToan paymentTM = hinhThucThanhToanRepository.findById(1L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
        BigDecimal threshold = new BigDecimal("100000000");
        if (request.getTongTienPhaiTra().compareTo(threshold) > 0) {
            System.out.println("Lớn 100 ");
            request.setTienChuyenKhoan(threshold);
            BigDecimal soTienConNo = request.getTongTienPhaiTra().subtract(threshold);
            System.out.println("Lớn 100tr " + soTienConNo);
            HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
            paymentHistory11.setSoTien(request.getTongTienPhaiTra());
            paymentHistory11.setTienNhan(threshold);
            paymentHistory11.setHoaDon(bill);
            paymentHistory11.setNguoiTao("CK");
            paymentHistory11.setNguoiSua("CK");
            paymentHistory11.setLoaiThanhToan(0);
            paymentHistory11.setTrangThai(1);
            paymentHistory11.setHinhThucThanhToan(paymentCK);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
            // thanh toán sau
            HoaDonHinhThucThanhToan paymentHistory2 = new HoaDonHinhThucThanhToan();
            paymentHistory2.setSoTien(request.getTongTienPhaiTra());
            paymentHistory2.setTienNhan(soTienConNo);
            paymentHistory2.setHoaDon(bill);
            paymentHistory2.setNguoiTao("Nợ");
            paymentHistory2.setNguoiSua("Nợ");
            paymentHistory2.setLoaiThanhToan(1);
            paymentHistory11.setTrangThai(1);

            paymentHistory2.setHinhThucThanhToan(paymentTM);
            hoaDonHinhThucThanhToanRepository.save(paymentHistory2);
        } else {
            if (request.getTongTienPhaiTra().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
                System.out.println("Dưới 100 và  lẻ");
                BigDecimal tongTienPhaiTra = request.getTongTienPhaiTra();
                BigDecimal phanNguyen = tongTienPhaiTra.setScale(0, BigDecimal.ROUND_DOWN);
                BigDecimal phanThapPhan = tongTienPhaiTra.subtract(phanNguyen);
                request.setTienChuyenKhoan(phanNguyen);
                //
                HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
                paymentHistory11.setSoTien(request.getTongTienPhaiTra());
                paymentHistory11.setTienNhan(phanNguyen);
                paymentHistory11.setHoaDon(bill);
                paymentHistory11.setNguoiTao("CK");
                paymentHistory11.setNguoiSua("CK");
                paymentHistory11.setLoaiThanhToan(0);
                paymentHistory11.setTrangThai(1);
                paymentHistory11.setHinhThucThanhToan(paymentCK);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
                // phần lẻ
                System.out.println("Phần thập phân: " + phanThapPhan);
                HoaDonHinhThucThanhToan paymentHistory2 = new HoaDonHinhThucThanhToan();
                paymentHistory2.setSoTien(request.getTongTienPhaiTra());
                paymentHistory2.setTienNhan(phanThapPhan);
                paymentHistory2.setHoaDon(bill);
                paymentHistory2.setNguoiTao("Nợ");
                paymentHistory2.setNguoiSua("Nợ");
                paymentHistory2.setLoaiThanhToan(1);
                paymentHistory2.setHinhThucThanhToan(paymentTM);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory2);
            } else {
                System.out.println("Dưới 100tr và ko lẻ");
                request.setTienChuyenKhoan(request.getTongTienPhaiTra());
                HoaDonHinhThucThanhToan paymentHistory11 = new HoaDonHinhThucThanhToan();
                paymentHistory11.setSoTien(request.getTongTienPhaiTra());
                paymentHistory11.setTienNhan(request.getTongTienPhaiTra());
                paymentHistory11.setHoaDon(bill);
                paymentHistory11.setNguoiTao("CK");
                paymentHistory11.setNguoiSua("CK");
                paymentHistory11.setLoaiThanhToan(0);
                paymentHistory11.setTrangThai(1);
                HinhThucThanhToan payment11 = hinhThucThanhToanRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.PAY_NO_FOUND));
                paymentHistory11.setHinhThucThanhToan(payment11);
                hoaDonHinhThucThanhToanRepository.save(paymentHistory11);
            }
        }
    }

    @Override
    public TraCuDonHangResponse lookUpOrders(String billCode, String phoneNumber) throws AppException {
        Optional<HoaDon> optional = billRepository.findByMaAndSdt(billCode, phoneNumber);
        if (!optional.isPresent()) {
            throw new AppException(ErrorCode.BILL_NOT_FOUND);
        }
        HoaDonResponse hoaDonResponse = HoaDonMapper.toHoaDonResponse(optional.get());
        hoaDonResponse.setTongTienPhaiTra(hoaDonResponse.getTongTienPhaiTra().add(hoaDonResponse.getTienShip()));
        List<LichSuHoaDonResponse> lichSuHoaDonResponses = billHistoryService.getBillHistoryByBillCode(optional.get().getMa());
        List<HoaDonHinhThucThanhToanResponse> lichSuThanhToan = hoaDonHinhThucThanhToanSerive.getAllByBillCode(optional.get().getMa());
        List<SerialNumberDaBanResponse> serialNumber = serialNumberDaBanService.getSerialNumberDaBanPage(optional.get().getMa());
        return TraCuDonHangResponse.builder().hoaDon(hoaDonResponse).lichSuHoaDon(lichSuHoaDonResponses).lichSuThanhToan(lichSuThanhToan).serialNumber(serialNumber).build();
    }

    @Override
    public List<HoaDonClientResponse> getAllByCustomerIdAndStatus(Long customerId, String status) throws AppException {
        List<HoaDonClientResponse> result = new ArrayList<>();
        List<HoaDon> bills = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            bills = billRepository.findAllByTrangThaiAndKhachHangId(HoaDonStatus.getHoaDonStatusEnumByKey(status), customerId);
        } else {
            bills = billRepository.findAllByKhachHangId(customerId);
        }
        for (HoaDon bill : bills) {
            List<LichSuHoaDonResponse> lichSuHoaDonResponses = billHistoryService.getBillHistoryByBillCode(bill.getMa());
            List<SerialNumberDaBanResponse> serialNumber = serialNumberDaBanService.getSerialNumberDaBanPage(bill.getMa());
            HoaDonResponse hoaDonResponse = HoaDonMapper.toHoaDonResponse(bill);
            hoaDonResponse.setTongTienPhaiTra(hoaDonResponse.getTongTienPhaiTra().add(hoaDonResponse.getTienShip()));
            HoaDonClientResponse response = HoaDonClientResponse.builder().hoaDon(hoaDonResponse).lichSuHoaDon(lichSuHoaDonResponses).serialNumber(serialNumber).build();

            result.add(response);
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public HoaDonClientResponse getBillDetail(String billCode) throws AppException {
        Optional<HoaDon> optional = hoaDonRepository.findHoaDonByMa(billCode);
        if (optional.isPresent()) {
            List<LichSuHoaDonResponse> lichSuHoaDonResponses = billHistoryService.getBillHistoryByBillCode(optional.get().getMa());
            List<SerialNumberDaBanResponse> serialNumber = serialNumberDaBanService.getSerialNumberDaBanPage(optional.get().getMa());
            HoaDonResponse hoaDonResponse = HoaDonMapper.toHoaDonResponse(optional.get());
            hoaDonResponse.setTongTienPhaiTra(hoaDonResponse.getTongTienPhaiTra().add(hoaDonResponse.getTienShip()));
            return HoaDonClientResponse.builder().hoaDon(hoaDonResponse).lichSuHoaDon(lichSuHoaDonResponses).serialNumber(serialNumber).build();
        }

        return HoaDonClientResponse.builder().build();
    }

    @Override
    public BigDecimal totalPriceByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepository.totalPriceByDate(startDate, endDate);
    }

    @Override
    public BigDecimal totalPriceByDateNow() {
        return hoaDonRepository.totalPriceByDateNow();
    }

//    @Override
//    public HoaDonSummaryDTO getInvoiceCountAndTotalProducts(LocalDateTime startDate, LocalDateTime endDate) {
//        return hoaDonRepository.getInvoiceCountAndTotalProducts(startDate, endDate);
//    }

    @Override
    public List<HoaDonDashboard_Dto> infoBillByDate(LocalDateTime startDateTime, LocalDateTime endDateTime) throws AppException {
        List<Object[]> results = billRepository.countInvoicesAndSumProductsByDate(startDateTime, endDateTime);
        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        List<HoaDonDashboard_Dto> hoaDonDashboardDtoList = new ArrayList<>();
        for (Object[] result : results) {
            java.sql.Date sqlDate = (java.sql.Date) result[0];
            LocalDateTime ngayThanhToan = sqlDate.toLocalDate().atStartOfDay(); // Chuyển đổi

            Long soHoaDon = (Long) result[1];
            Long tongSanPham = (Long) result[2];

            HoaDonDashboard_Dto response = new HoaDonDashboard_Dto();
            response.setNgayThanhToan(ngayThanhToan);
            response.setTongHoaDon(soHoaDon);
            response.setTongSanPham(tongSanPham);

            hoaDonDashboardDtoList.add(response);
        }
        return hoaDonDashboardDtoList;
    }

    @Override
    public Long sumProductSoldOutByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepository.sumProductSoldOut(startDate, endDate);
    }

    @Override
    public List<TrangThaiHoaDon_Dto> CalculateBillPercentage(LocalDateTime startDate, LocalDateTime endDate) throws AppException {
        Long startDateMillis = startDate.toInstant(ZoneOffset.UTC).toEpochMilli();
        Long endDateMillis = endDate.toInstant(ZoneOffset.UTC).toEpochMilli();
        List<Object[]> trangThaiHoaDonCalulate = hoaDonRepository.totalCalculateBillPercentageByDate(startDateMillis, endDateMillis);
        if (trangThaiHoaDonCalulate.isEmpty()) {
            return new ArrayList<>();
        }

        List<TrangThaiHoaDon_Dto> listTrangThai = new ArrayList<>();
        for (Object[] result : trangThaiHoaDonCalulate) {
            Long soLuong = ((Number) result[0]).longValue();
            Integer trangThai = result[1] != null ? ((Number) result[1]).intValue() : null;
            BigDecimal tiLe = (result[2] instanceof Double) ? BigDecimal.valueOf((Double) result[2]) : (BigDecimal) result[2];

            TrangThaiHoaDon_Dto response = new TrangThaiHoaDon_Dto();
            response.setSoLuong(soLuong);
            response.setTrangThaiHoaDon(trangThai);
            response.setTiLeTrangThaiHoaDon(tiLe);
            listTrangThai.add(response);
        }

        return listTrangThai;
    }

    @Override
    public void updateCoupons(PhieuGiamGia phieuGiamGia, HoaDon bill) {
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
                if (coupon.getPhamViApDung() == 2) {
                    Optional<KhachHangPhieuGiamGia> optional = khachHangPhieuGiamGiaRepository.findKhachHangPhieuGiamGiaByPhieuGiamGiaIdAndKhachHangId(coupon.getId(), bill.getKhachHang().getId());
                    optional.get().setTrangThai(1);// 1 đã dùng
                    khachHangPhieuGiamGiaRepository.save(optional.get());
                }
            }
        }
    }


    private BigDecimal calculateDiscount(HoaDon existingBill, PhieuGiamGia coupon) {
        BigDecimal moneyReduced = BigDecimal.ZERO;
        // 1 % : 2 VND
        if (coupon.getLoaiGiamGia() == 2) {
            moneyReduced = coupon.getGiaTriGiamGia();
        } else {
            moneyReduced = coupon.getGiamToiDa().multiply(coupon.getGiaTriGiamGia()).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
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
        //
        if (existingBill.getKhachHang() != null) {
            BigDecimal rank = RankCustomer.getValueByRank(existingBill.getKhachHang().getHangKhachHang());
            existingBill.setTienGiamHangKhachHang(rank);
            BigDecimal newTongTienPhaiTra = existingBill.getTongTienPhaiTra().subtract(rank);
            if (newTongTienPhaiTra.compareTo(BigDecimal.ZERO) < 0) {
                newTongTienPhaiTra = BigDecimal.ZERO;
                existingBill.setTongTienPhaiTra(newTongTienPhaiTra);
            } else {
                existingBill.setTongTienPhaiTra(newTongTienPhaiTra);
            }
        }
    }

    private Optional<PhieuGiamGia> getPhieuGiamGia(HoaDon hoaDon, BigDecimal tongTien) {
        if (hoaDon.getKhachHang() == null) {
            return couponRepository.getHighestDiscountVoucherByTotalAmount(tongTien);
        } else {
            return couponRepository.getHighestDiscountVoucherByTotalAmountAndCustomer(tongTien, hoaDon.getKhachHang().getId());
        }
    }

    private void productRefund(HoaDon bill) {
        List<Long> listSerialNumberIdInBill = serialNumberDaBanRepository.getAllSerialNumberInBillByBillId(bill.getId());
        List<SerialNumber> serialNumbers = serialNumberRepository.findAllById(listSerialNumberIdInBill);
        for (SerialNumber serialNumber : serialNumbers) {
            serialNumber.setTrangThai(0);
        }
        serialNumberRepository.saveAll(serialNumbers);
    }


}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.constant.EmailSender;
import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.KhachHangPhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.PhieuGiamGiaMapper;
import com.dantn.weblaptop.repository.KhachHangPhieuGiamGiaRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.NhanVien_Repositoy;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PhieuGiamGiaService {
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;
    @Autowired
    private KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private NhanVien_Repositoy nhanVienRepositoy;
    @Autowired
    private View error;

    public ResultPaginationResponse filterCoupons(Specification<PhieuGiamGia> specification, Pageable pageable) {
        Page<PhieuGiamGia> couponPage = phieuGiamGiaRepo.findAll(specification, pageable);
        Page<PhieuGiamGiaResponse> responses = couponPage.map(
                PhieuGiamGiaMapper::toPhieuGiamGiaResponse);
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

    public ResultPaginationResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        Page<PhieuGiamGia> phieuGiamGiaPage = phieuGiamGiaRepo.findAll(pageable);
        Page<PhieuGiamGiaResponse> responses = phieuGiamGiaPage.map(
                PhieuGiamGiaMapper::toPhieuGiamGiaResponse);
        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        // trả về list

        return ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())// trả về list
                .build();
    }

    private String generateUniqueCode() {
        return "PGG" + System.currentTimeMillis();
    }

    @Transactional
    public PhieuGiamGiaResponse add(CreatePhieuGiamGiaRequest request) throws AppException {
        PhieuGiamGia newPhieuGiamGia = PhieuGiamGiaMapper.toCreatePGG(request);
        validateFormDataCreate(newPhieuGiamGia, request);
        newPhieuGiamGia.setMa(generateUniqueCode());
        if (request.getSoLuong() != null && request.getSoLuong() == 0) {
            newPhieuGiamGia.setTrangThai(2);
        }
        PhieuGiamGia savedPhieuGiamGia = phieuGiamGiaRepo.save(newPhieuGiamGia);
        List<Long> listKhachHangIds = request.getListKhachHang();
        if (listKhachHangIds != null) {
            listKhachHangIds.forEach(id -> {
                try {
                    sendMailToCustomer(savedPhieuGiamGia, id);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepo.findById(savedPhieuGiamGia.getId()).get();

        return PhieuGiamGiaMapper.toPhieuGiamGiaResponse(phieuGiamGia);
    }

    private void sendMailToCustomer(PhieuGiamGia savedPhieuGiamGia, Long id) throws MessagingException {
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
        if (khachHang != null) {
            KhachHangPhieuGiamGia khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
            khachHangPhieuGiamGia.setPhieuGiamGia(savedPhieuGiamGia);
            khachHangPhieuGiamGia.setKhachHang(khachHang);
            khachHangPhieuGiamGia.setTrangThai(0);  // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
            khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
                    if (khachHang.getEmail() != null) {
                        emailSender.sendEmailCoupons(khachHang, savedPhieuGiamGia);
                    }
        }
    }

    public PhieuGiamGiaResponse update(UpdatePhieuGiamGiaRequest request, Long id) throws AppException, MessagingException {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        if (!optional.isPresent()) {
            throw new AppException(ErrorCode.COUPONS_NOT_FOUND);
        }

        PhieuGiamGia phieuGiamGia = optional.get();
        validateFormDataUpdate(phieuGiamGia, request);
        PhieuGiamGiaMapper.toUpdatePGG(request, phieuGiamGia);
        Set<KhachHangPhieuGiamGia> existingRelations = khachHangPhieuGiamGiaRepository.findByPhieuGiamGiaId(phieuGiamGia.getId());
        List<Long> existingKhachHangIds = existingRelations.stream()
                .map(relation -> relation.getKhachHang().getId())
                .toList();

        List<Long> newKhachHangIds = request.getListKhachHang();
        if (request.getSoLuong() != null && request.getSoLuong() == 0) {
            phieuGiamGia.setTrangThai(2);
        }
        PhieuGiamGia savedPhieuGiamGia = phieuGiamGiaRepo.save(phieuGiamGia);
        for (KhachHangPhieuGiamGia relation : existingRelations) {
            if (!newKhachHangIds.contains(relation.getKhachHang().getId())) {
                khachHangPhieuGiamGiaRepository.delete(relation);
            }
        }
        // Thêm các khách hàng mới
        for (Long khachHangId : newKhachHangIds) {
            if (!existingKhachHangIds.contains(khachHangId)) {
                sendMailToCustomer(phieuGiamGia, khachHangId);
            }else {
                System.out.println(error);
            }
        }
        // Tạo phản hồi
        return PhieuGiamGiaMapper.toPhieuGiamGiaResponse(savedPhieuGiamGia);
    }


    public PhieuGiamGia delete(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTrangThai(3);   // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
            Set<KhachHangPhieuGiamGia> khachHangPhieuGiamGias = khachHangPhieuGiamGiaRepository.findByPhieuGiamGiaId(id);
            khachHangPhieuGiamGiaRepository.deleteAll(khachHangPhieuGiamGias);
            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }

    public PhieuGiamGiaResponse detail(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(PhieuGiamGiaMapper::toPhieuGiamGiaResponse).orElse(null);
    }

    /// page pgg với id khach hàng
    public ResultPaginationResponse getKhPGGById(Long id, Optional<String> page, Optional<String> size) throws AppException {
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepo.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.COUPONS_NOT_FOUND));
        String sPage = page.orElse("0");
        String sSize = size.orElse("5");
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<KhachHangPhieuGiamGia> khachHangPhieuGiamGias = khachHangPhieuGiamGiaRepository.findByPhieuGiamGiaId(id, pageable);
        Page<KhachHangPhieuGiamGiaResponse> responses = khachHangPhieuGiamGias.map(
                PhieuGiamGiaMapper::toKhachHangPhieuGiamGiaResponse
        );
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

    public void updateStatusKhachHangPhieuGiamGia(Long idKHPGG, Integer trangThai) throws AppException {
        Optional<KhachHangPhieuGiamGia> optional = khachHangPhieuGiamGiaRepository.findById(idKHPGG);
        if (optional.isPresent()) {
            KhachHangPhieuGiamGia khachHangPhieuGiamGia = optional.get();
            khachHangPhieuGiamGia.setTrangThai(trangThai);
            khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
        }
    }

    // lặp code chưa sửa
    // validate form data
    private void validateFormDataCreate(PhieuGiamGia phieuGiamGia, CreatePhieuGiamGiaRequest request) throws AppException {
        if (request.getMa() != null && phieuGiamGiaRepo.existsByMa(request.getMa())) {
            throw new AppException(ErrorCode.COUPON_CODE_ALREADY_EXISTS);
        } else {
            phieuGiamGia.setMa(request.getMa() == null ? generateUniqueCode() : request.getMa());
        }
        if (request.getLoaiGiamGia() == 1 && request.getGiaTriGiamGia().compareTo(new BigDecimal("100")) > 0) {
            throw new AppException(ErrorCode.COUPONS_MAXIMUM_100);
        }
        if (request.getNgayHetHan().isBefore(request.getNgayBatDau())) {
            throw new AppException(ErrorCode.ERROR_DATE_1);
        }

        long currentSeconds = System.currentTimeMillis() / 1000;
        long ngayBatDauSeconds = request.getNgayBatDau().toEpochSecond(ZoneOffset.UTC);
        long ngayHetHanSeconds = request.getNgayHetHan().toEpochSecond(ZoneOffset.UTC);
        if (ngayHetHanSeconds <= currentSeconds) {
            throw new AppException(ErrorCode.ERROR_DATE_2);
        }
        // chỉnh sử trạng thái
        Integer status = (ngayBatDauSeconds > currentSeconds) ? 0 :
                (currentSeconds >= ngayHetHanSeconds ? 2 : 1);
        phieuGiamGia.setTrangThai(status);

    }

    private void validateFormDataUpdate(PhieuGiamGia phieuGiamGia, UpdatePhieuGiamGiaRequest request) throws AppException {

        if (request.getLoaiGiamGia() == 1 && request.getGiaTriGiamGia().compareTo(new BigDecimal("100")) > 0) {
            throw new AppException(ErrorCode.COUPONS_MAXIMUM_100);
        }
        // Kiểm tra ngày kết thúc và ngày bắt đầu
        if (request.getNgayHetHan().isBefore(request.getNgayBatDau())) {
            throw new AppException(ErrorCode.ERROR_DATE_1);
        }
        long currentSeconds = System.currentTimeMillis() / 1000;
        long ngayBatDauSeconds = request.getNgayBatDau().toEpochSecond(ZoneOffset.UTC);
        long ngayHetHanSeconds = request.getNgayHetHan().toEpochSecond(ZoneOffset.UTC);
        if (ngayHetHanSeconds <= currentSeconds) {
            throw new AppException(ErrorCode.ERROR_DATE_2);
        }
        // chỉnh sử trạng thái
        Integer status = (ngayBatDauSeconds > currentSeconds) ? 0 :
                (currentSeconds >= ngayHetHanSeconds ? 2 : 1);
        phieuGiamGia.setTrangThai(status);
    }

}

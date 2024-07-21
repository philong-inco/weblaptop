package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.*;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.mapper.impl.HoaDonMapper;
import com.dantn.weblaptop.mapper.impl.PhieuGiamGiaMapper;
import com.dantn.weblaptop.repository.KhachHangPhieuGiamGiaRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import com.dantn.weblaptop.util.ConvertTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PhieuGiamGiaService {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;
    @Autowired

    private KhachHangRepository khachHangRepository;

    public ResultPaginationResponse filterCoupons (Specification<PhieuGiamGia> specification, Pageable pageable){
        Page<PhieuGiamGia> couponPage = phieuGiamGiaRepo.findAll(specification, pageable);
        Page<PhieuGiamGiaResponse> responses = couponPage.map(
                coupon -> PhieuGiamGiaMapper.toPhieuGiamGiaResponse(coupon));
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

    public ResultPaginationResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        Page<PhieuGiamGia> phieuGiamGiaPage = phieuGiamGiaRepo.findAll(pageable);
        // chuyển thành response :
        Page<PhieuGiamGiaResponse> responses = phieuGiamGiaPage.map(
                phieuGiamGia -> PhieuGiamGiaMapper.toPhieuGiamGiaResponse(phieuGiamGia));
        // 2 đối tượng này dùng đề format lại dữ liệu trả về
        // mate : danh cho phân trang
        // ResultPaginationResponse : là kết quả trả về
        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())// trả về list
                .build();

        return response;
    }

    private String generateUniqueCode() {
        return "PGG" + System.currentTimeMillis();
    }

    public PhieuGiamGiaResponse add(CreatePhieuGiamGiaRequest request) throws AppException {
        PhieuGiamGia newPhieuGiamGia = PhieuGiamGiaMapper.toCreatePGG(request);
        newPhieuGiamGia.setMa(request.getMa() == null ? generateUniqueCode() : request.getMa());
        if (ConvertTime.convertStringToLong2(request.getNgayHetHan() + "")
                <= ConvertTime.convertStringToLong2(request.getNgayBatDau() + "")) {
            throw new AppException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }
        long currentSeconds = (System.currentTimeMillis() / 1000) * 1000;
        if (ConvertTime.convertStringToLong2(request.getNgayHetHan() + "") <= currentSeconds) {
            throw new AppException("Ngày kết thúc phải lớn hơn hiện tại");
        }
        // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
        Integer status = (ConvertTime.convertStringToLong2(request.getNgayBatDau() + "") > currentSeconds) ? 0 : (currentSeconds >= ConvertTime.convertStringToLong2(request.getNgayHetHan() + "") ? 0 : 1);
        newPhieuGiamGia.setTrangThai(status);
        // sau check mã trùng
        PhieuGiamGia savedPhieuGiamGia = phieuGiamGiaRepo.save(newPhieuGiamGia);
        request.getListKhachHang().forEach(id -> {
            KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
            if (khachHang != null) {
                KhachHangPhieuGiamGia khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
                khachHangPhieuGiamGia.setPhieuGiamGia(savedPhieuGiamGia);
                khachHangPhieuGiamGia.setKhachHang(khachHang);
                khachHangPhieuGiamGia.setTrangThai(0);  // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
                khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
                // send mail
            }
        });
        PhieuGiamGiaResponse response = PhieuGiamGiaMapper.toPhieuGiamGiaResponse(savedPhieuGiamGia);
        return response;
    }

//    public PhieuGiamGiaResponse update(UpdatePhieuGiamGiaRequest request, Long id) {
//        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
//        if (optional.isPresent()) {
//            PhieuGiamGiaMapper.toUpdatePGG(request, optional.get());
//            return PhieuGiamGiaMapper.toPhieuGiamGiaResponse(phieuGiamGiaRepo.save(optional.get()));
//        }
//        return null;
//    }

    public PhieuGiamGiaResponse update(UpdatePhieuGiamGiaRequest request, Long id) throws AppException {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        if (!optional.isPresent()) {
            throw new AppException("Phiếu giảm giá không tồn tại");
        }

        PhieuGiamGia phieuGiamGia = optional.get();

        // Cập nhật thông tin phiếu giảm giá
        phieuGiamGia.setMa(request.getMa() == null ? generateUniqueCode() : request.getMa());
        phieuGiamGia.setTen(request.getTen());
        phieuGiamGia.setMoTa(request.getMoTa());
        phieuGiamGia.setNgayBatDau(request.getNgayBatDau());
        phieuGiamGia.setNgayHetHan(request.getNgayHetHan());
        phieuGiamGia.setLoaiGiamGia(request.getLoaiGiamGia());
        phieuGiamGia.setGiaTriGiamGia(request.getGiaTriGiamGia());
        phieuGiamGia.setGiaTriDonToiThieu(request.getGiaTriDonToiThieu());
        phieuGiamGia.setGiamToiGia(request.getGiamToiGia());
        phieuGiamGia.setPhamViApDung(request.getPhamViApDung());
        phieuGiamGia.setSoLuong(request.getSoLuong());
        phieuGiamGia.setNgaySua(System.currentTimeMillis());
        phieuGiamGia.setNguoiSua(request.getNguoiSua());

        // Kiểm tra ngày kết thúc và ngày bắt đầu
        if (request.getNgayHetHan().isBefore(request.getNgayBatDau())) {
            throw new AppException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }

        long currentSeconds = System.currentTimeMillis() / 1000;
        if (request.getNgayHetHan().toEpochDay() * 86400 <= currentSeconds) {
            throw new AppException("Ngày kết thúc phải lớn hơn hiện tại");
        }

        Integer status = (request.getNgayBatDau().toEpochDay() * 86400 > currentSeconds) ? 0 :
                (currentSeconds >= request.getNgayHetHan().toEpochDay() * 86400 ? 2 : 1);
        phieuGiamGia.setTrangThai(status);

        // Cập nhật danh sách khách hàng liên kết với phiếu giảm giá
        Set<KhachHangPhieuGiamGia> existingRelations = khachHangPhieuGiamGiaRepository.findByPhieuGiamGiaId(phieuGiamGia.getId());
        List<Long> existingKhachHangIds = existingRelations.stream()
                .map(relation -> relation.getKhachHang().getId())
                .toList();

        List<Long> newKhachHangIds = request.getListKhachHang();

        // Thêm các khách hàng mới
        for (Long khachHangId : newKhachHangIds) {
            if (!existingKhachHangIds.contains(khachHangId)) {
                KhachHang khachHang = khachHangRepository.findById(khachHangId).orElse(null);
                if (khachHang != null) {
                    KhachHangPhieuGiamGia khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
                    khachHangPhieuGiamGia.setPhieuGiamGia(phieuGiamGia);
                    khachHangPhieuGiamGia.setKhachHang(khachHang);
                    khachHangPhieuGiamGia.setTrangThai(0); // 0 chưa dùng : 1 đang áp dụng : 2 hết hạn : 3 hủy
                    khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
                }
            }
        }

        // Xóa các khách hàng không còn trong danh sách mới
        for (KhachHangPhieuGiamGia relation : existingRelations) {
            if (!newKhachHangIds.contains(relation.getKhachHang().getId())) {
                khachHangPhieuGiamGiaRepository.delete(relation);
            }
        }

        // Lưu phiếu giảm giá đã cập nhật vào cơ sở dữ liệu
        PhieuGiamGia savedPhieuGiamGia = phieuGiamGiaRepo.save(phieuGiamGia);

        // Tạo phản hồi
        PhieuGiamGiaResponse response = PhieuGiamGiaMapper.toPhieuGiamGiaResponse(savedPhieuGiamGia);
        return response;
    }


    public PhieuGiamGia delete(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTrangThai(3);   // 0 chưa dung : 1 đang áp dụng : 2 : hết hạn : 3 hủy
            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }

    public PhieuGiamGiaResponse detail(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        if (optional.isPresent()) {
            return  PhieuGiamGiaMapper.toPhieuGiamGiaResponse(optional.get());
        }
        return null;
    }

    /// page pgg với id khach hàng
    public ResultPaginationResponse getKhPGGById(Long id, Optional<String> page, Optional<String> size) throws AppException {
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepo.findById(id).orElseThrow(
                () -> new AppException("Không tìm thấy phiếu giảm giá với id :" + id));
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<KhachHangPhieuGiamGia> khachHangPhieuGiamGias = khachHangPhieuGiamGiaRepository.findByPhieuGiamGiaId(id, pageable);
        Page<KhachHangPhieuGiamGiaResponse> responses = khachHangPhieuGiamGias.map(
                khpgg -> PhieuGiamGiaMapper.toKhachHangPhieuGiamGiaResponse(khpgg)
        );
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

    public void updateStatusKhachHangPhieuGiamGia(Long idKHPGG, Integer trangThai) throws AppException {
        Optional<KhachHangPhieuGiamGia> optional = khachHangPhieuGiamGiaRepository.findById(idKHPGG);
        if (optional.isPresent()) {
            KhachHangPhieuGiamGia khachHangPhieuGiamGia = optional.get();
            khachHangPhieuGiamGia.setTrangThai(trangThai);
            khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
        }
    }

}

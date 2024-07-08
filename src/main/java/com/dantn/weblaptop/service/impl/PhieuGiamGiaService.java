package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.KhachHangPhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.mapper.impl.PhieuGiamGiaMapper;
import com.dantn.weblaptop.repository.KhachHangPhieuGiamGiaRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.PhieuGiamGiaRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuGiamGiaService {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;
    @Autowired

    private KhachHangRepository khachHangRepository;

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

    public PhieuGiamGiaResponse add(CreatePhieuGiamGiaRequest request) {
        PhieuGiamGia newPhieuGiamGia = PhieuGiamGiaMapper.toCreatePGG(request);
        newPhieuGiamGia.setMa(generateUniqueCode());
        PhieuGiamGia savedPhieuGiamGia = phieuGiamGiaRepo.save(newPhieuGiamGia);
        request.getListKhachHang().forEach(id -> {
            KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
            if (khachHang != null) {
                KhachHangPhieuGiamGia khachHangPhieuGiamGia = new KhachHangPhieuGiamGia();
                khachHangPhieuGiamGia.setPhieuGiamGia(savedPhieuGiamGia);
                khachHangPhieuGiamGia.setKhachHang(khachHang);
                khachHangPhieuGiamGia.setTrangThai(0); // 0 chưa dùng
                khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
            }
        });
        PhieuGiamGiaResponse response = PhieuGiamGiaMapper.toPhieuGiamGiaResponse(savedPhieuGiamGia);
        return response;
    }

    public PhieuGiamGia update(PhieuGiamGia phieuGiamGia, Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTen(phieuGiamGia.getTen());
            o.setGiamToiGia(phieuGiamGia.getGiamToiGia());
            o.setGiaTriGiamGia(phieuGiamGia.getGiaTriGiamGia());
            o.setNgayBatDau(phieuGiamGia.getNgayBatDau());
            o.setNgayHetHan(phieuGiamGia.getNgayHetHan());
            o.setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
            o.setTrangThai(phieuGiamGia.getTrangThai());
            o.setMoTa(phieuGiamGia.getMoTa());
            o.setGiaTriDonToiThieu(phieuGiamGia.getGiaTriDonToiThieu());

            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }


    public PhieuGiamGia delete(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        return optional.map(o -> {
            o.setTrangThai(0); // Giả sử trạng thái "Ngừng hoạt động" = 0
            return phieuGiamGiaRepo.save(o);
        }).orElse(null);
    }

    public PhieuGiamGiaResponse detail(Long id) {
        Optional<PhieuGiamGia> optional = phieuGiamGiaRepo.findById(id);
        if (optional.isPresent()) {
            return PhieuGiamGiaMapper.toPhieuGiamGiaResponse(optional.get());
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

    public void updateStatusKhachHangPhieuGiamGia(Long idKHPGG , Integer trangThai) throws AppException {
     Optional<KhachHangPhieuGiamGia> optional = khachHangPhieuGiamGiaRepository.findById(idKHPGG);
     if(optional.isPresent()){
         KhachHangPhieuGiamGia khachHangPhieuGiamGia = optional.get();
         khachHangPhieuGiamGia.setTrangThai(trangThai);
         khachHangPhieuGiamGiaRepository.save(khachHangPhieuGiamGia);
     }
    }

}

package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.repository.DotGiamGiaSanPhamChiTiet_Repository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;
    @Autowired
    private DotGiamGiaMapper dotGiamGiaMapper;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private DotGiamGiaSanPhamChiTiet_Repository dotGiamGiaSanPhamChiTietRepository;
    @Override
    public Page<DotGiamGiaResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DotGiamGia> dotGiamGiaPage = dotGiamGiaRepository.finAllDotGiamGia(pageable);
        Page<DotGiamGiaResponse> responses = dotGiamGiaMapper.findAllRequestToDotGiamGiaResponse(dotGiamGiaPage);
        return responses;
    }

    @Override
    public DotGiamGiaResponse findById(Long id) {
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Đợt Giảm Giá Không Tồn Tại !"));
        DotGiamGiaResponse dotGiamGiaResponse = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return dotGiamGiaResponse;
    }

    @Override
    public DotGiamGiaResponse save(CreateDotGiamGiaRequest request) {
        DotGiamGia dotGiamGiaGetByName = dotGiamGiaRepository.findbyNameAndMa(request.getTen(), request.getMa());
        if (dotGiamGiaGetByName != null) {
            throw new RuntimeException("Tên Hoặc Mã Đợt Giảm Giá : Đã Tồn Tại");
        }
        DotGiamGia dotGiamGia = dotGiamGiaMapper.createRequestToDotGiamGia(request);
        dotGiamGiaRepository.saveAndFlush(dotGiamGia);
        DotGiamGiaResponse dotGiamGiaResponse = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return dotGiamGiaResponse;
    }

    @Override
    public DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request) {
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Đợt Giảm Giá Không Tồn Tại"));
        DotGiamGia checkName = dotGiamGiaRepository.findByNameOrMaExcludingId(id, request.getTen(), request.getMa());
        if (checkName != null) {
            throw new RuntimeException(" Update Không Thành Công Tên Hoặc Ma Đợt Giảm Giá : Đã Tồn Tại");
        }
        dotGiamGia = dotGiamGiaMapper.updateDotGiamGia(dotGiamGia, request);
        dotGiamGiaRepository.save(dotGiamGia);
        DotGiamGiaResponse response = dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
        return response;
    }

    @Override
    public Page<DotGiamGiaResponse> filter(String tenOrMa, Integer giaTri, Integer trangThai, LocalDateTime startDay, LocalDateTime endDay, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DotGiamGia> dotGiamGiaPage = dotGiamGiaRepository.filterAllDiscount(pageable, tenOrMa, giaTri, trangThai, startDay, endDay);
        Page<DotGiamGiaResponse> responses = dotGiamGiaMapper.findAllRequestToDotGiamGiaResponse(dotGiamGiaPage);
        return responses;
    }

    @Override
    public void changeStatusDotGiamGia(Long id) {
        dotGiamGiaRepository.updateStatusDGG(id);
    }

    private String generateUniqueCode() {
        return "DGG" + System.currentTimeMillis();
    }
    private void validateFormDataCreate(DotGiamGia dotGiamGia, CreateDotGiamGiaRequest request) throws AppException {
        if (request.getMa() != null && dotGiamGiaRepository.existsByMa(request.getMa())) {
            throw new AppException(ErrorCode.COUPON_CODE_ALREADY_EXISTS);
        } else {
            dotGiamGia.setMa(request.getMa() == null ? generateUniqueCode() : request.getMa());
        }
        if (request.getGiaTriGiam()>0) {
            throw new AppException(ErrorCode.COUPONS_MAXIMUM_100);
        }
        if (request.getThoiGianBatDau().isBefore(request.getThoiGianKetThuc())) {
            throw new AppException(ErrorCode.ERROR_DATE_1);
        }

        long currentSeconds = System.currentTimeMillis() / 1000;
        long ngayBatDauSeconds = request.getThoiGianBatDau().toEpochSecond(ZoneOffset.UTC);
        long ngayHetHanSeconds = request.getThoiGianKetThuc().toEpochSecond(ZoneOffset.UTC);
        if (ngayHetHanSeconds <= currentSeconds) {
            throw new AppException(ErrorCode.ERROR_DATE_2);
        }
        // chỉnh sử trạng thái
        Integer status = (ngayBatDauSeconds > currentSeconds) ? 0 :
                (currentSeconds >= ngayHetHanSeconds ? 2 : 1);
        dotGiamGia.setTrangThai(status);
    }

    private void validateFormDataUpdate(DotGiamGia dotGiamGia, UpdateGotGiamGiaRequest request) throws AppException {
        if (request.getGiaTriGiam() > 0) {
            throw new AppException(ErrorCode.COUPONS_MAXIMUM_100);
        }
        // Kiểm tra ngày kết thúc và ngày bắt đầu
        if (request.getThoiGianBatDau().isBefore(request.getThoiGianKetthuc())) {
            throw new AppException(ErrorCode.ERROR_DATE_1);
        }
        long currentSeconds = System.currentTimeMillis() / 1000;
        long ngayBatDauSeconds = request.getThoiGianBatDau().toEpochSecond(ZoneOffset.UTC);
        long ngayHetHanSeconds = request.getThoiGianKetthuc().toEpochSecond(ZoneOffset.UTC);
        if (ngayHetHanSeconds <= currentSeconds) {
            throw new AppException(ErrorCode.ERROR_DATE_2);
        }
        // chỉnh sử trạng thái
        Integer status = (ngayBatDauSeconds > currentSeconds) ? 0 :
                (currentSeconds >= ngayHetHanSeconds ? 2 : 1);
        dotGiamGia.setTrangThai(status);
    }

    private void addSpct (DotGiamGia dotGiamGia, Long id){
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).orElse(null);
        if(sanPhamChiTiet != null){
            DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet = new DotGiamGiaSanPhamChiTiet();
            dotGiamGiaSanPhamChiTiet.setDotGiamGia(dotGiamGia);
            dotGiamGiaSanPhamChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            dotGiamGiaSanPhamChiTiet.setTrangThai(0);
            dotGiamGiaSanPhamChiTietRepository.save(dotGiamGiaSanPhamChiTiet);
        }
    }
}

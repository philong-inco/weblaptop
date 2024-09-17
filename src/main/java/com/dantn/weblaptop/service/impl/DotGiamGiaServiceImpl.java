package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;
    @Autowired
    private DotGiamGiaMapper dotGiamGiaMapper;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

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


        // cái hàm này em mới chỉ test get id sản phẩm hiển thị sản phẩm chi tiết nhưng mới chỉ lấy được 1 cần lấy 1 mảng
//    @Override
//    public Page<DotGiamGiaSanPhamChiTietResponse> timKiemSanPhamChiTietTheoIdSanPham(List<Long> idSanPham, Integer page, Integer size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<SanPhamChiTiet> sanPhamChiTietPage;
//
//        if (idSanPham == null || idSanPham.isEmpty()) {
//            sanPhamChiTietPage = sanPhamChiTietRepository.findAll(pageable);
//        } else {
//            sanPhamChiTietPage = dotGiamGiaRepository.timKiemSanPhamChiTietTheoIdSanPham(idSanPham, pageable);
//        }
//        Page<DotGiamGiaSanPhamChiTietResponse> responses = dotGiamGiaMapper.findSanPhamChiTietChoDotGiamGia(sanPhamChiTietPage);
//        return responses;
//    }
}

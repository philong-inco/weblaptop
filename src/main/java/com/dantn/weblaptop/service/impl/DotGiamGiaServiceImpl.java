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
import com.dantn.weblaptop.repository.KhachHangPhieuGiamGiaRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;


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
    @Autowired
    private KhachHangPhieuGiamGiaRepository khachHangPhieuGiamGiaRepository;
    @Autowired
    private View error;

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
    public DotGiamGiaResponse save(CreateDotGiamGiaRequest request) throws AppException {
        DotGiamGia newDotGiamGia = dotGiamGiaMapper.createRequestToDotGiamGia(request);
        validateFormDataCreate(newDotGiamGia, request);
        newDotGiamGia.setMa(generateUniqueCode());
        if (request.getGiaTriGiam() != null) {
            newDotGiamGia.setTrangThai(0);
        }
        DotGiamGia saveDotGiamGia = dotGiamGiaRepository.save(newDotGiamGia);
        List<Long> listSpctIds = request.getListSanPhamChiTiet();
        if (listSpctIds != null) {
            listSpctIds.forEach(id -> {
                try {
                    addSpct(saveDotGiamGia, id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        DotGiamGia dotGiamGia = dotGiamGiaRepository.findById(saveDotGiamGia.getId()).get();
        return dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(dotGiamGia);
    }

    @Override
    public DotGiamGiaResponse update(Long id, UpdateGotGiamGiaRequest request) throws AppException {
        DotGiamGia findDotGiamGia = dotGiamGiaRepository.findById(id).get();
        validateFormDataUpdate(findDotGiamGia, request);
        dotGiamGiaMapper.updateDotGiamGia(findDotGiamGia, request);
        Set<DotGiamGiaSanPhamChiTiet> existingRelations = dotGiamGiaSanPhamChiTietRepository.findByDotGiamGiaId(findDotGiamGia.getId());
        List<Long> existingSpctIds = existingRelations.stream().map(relation -> relation.getSanPhamChiTiet().getId()).toList();
        List<Long> newSpctIds = request.getListSanPhamChiTiet();
        if (newSpctIds != null) {
            findDotGiamGia.setTrangThai(0);
        }
        DotGiamGia saveDotGiamGia = dotGiamGiaRepository.save(findDotGiamGia);
        for (DotGiamGiaSanPhamChiTiet relation : existingRelations) {
            if (!newSpctIds.contains(relation.getSanPhamChiTiet().getId())) {
                dotGiamGiaSanPhamChiTietRepository.delete(relation);
            }
        }

        for (Long sanPhamChiTietId : newSpctIds) {
            if (!existingRelations.contains(sanPhamChiTietId)) {
                addSpct(findDotGiamGia, sanPhamChiTietId);
            } else {
                System.out.println(error);
            }
        }
        return dotGiamGiaMapper.dotGiamGiaToDotGiamGiaResponse(saveDotGiamGia);
    }

    @Override
    public Page<DotGiamGiaResponse> filter(String tenOrMa, Integer trangThai, LocalDateTime startDay, LocalDateTime endDay, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DotGiamGia> dotGiamGiaPage = dotGiamGiaRepository.filterAllDiscount(pageable, tenOrMa, trangThai, startDay, endDay);
        Page<DotGiamGiaResponse> responses = dotGiamGiaMapper.findAllRequestToDotGiamGiaResponse(dotGiamGiaPage);
        return responses;
    }

    @Override
    public void changeStatusDotGiamGia(Long id) {
        dotGiamGiaRepository.updateStatusDGG(id);
    }

    @Override

    public void changeStatusDotGiamGiaStart(Long id) {
        dotGiamGiaRepository.updateStatusDGGStart(id);
    }

    @Override
    public void changeStatusDotGiamGiaStop(Long id) {
        dotGiamGiaRepository.updateStatusDGGStop(id);
    }

    @Override
    public void deleteDotGiamGia(Long id) {
        dotGiamGiaRepository.deleteStatusDGGStop(id);

    public List<DotGiamGia> getDotGiamGiaBySPCTId(Long idSPCT) {
        return dotGiamGiaRepository.getDotGiamGiaBySPCTId(idSPCT);

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
        if (request.getThoiGianKetThuc().isBefore(request.getThoiGianBatDau())) {
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
        // Kiểm tra ngày kết thúc và ngày bắt đầu
        if (request.getThoiGianKetThuc().isBefore(request.getThoiGianBatDau())) {
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

    private void addSpct(DotGiamGia dotGiamGia, Long id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).orElse(null);
        if (sanPhamChiTiet != null) {
            DotGiamGiaSanPhamChiTiet dotGiamGiaSanPhamChiTiet = new DotGiamGiaSanPhamChiTiet();
            dotGiamGiaSanPhamChiTiet.setDotGiamGia(dotGiamGia);
            dotGiamGiaSanPhamChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            dotGiamGiaSanPhamChiTiet.setTrangThai(0);
            dotGiamGiaSanPhamChiTietRepository.save(dotGiamGiaSanPhamChiTiet);
        }
    }
}

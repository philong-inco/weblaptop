package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.create_request.FilterDotGiamGia;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.generics.GenericsSpecificationAttributeInner;
import com.dantn.weblaptop.repository.DotGiamGiaRepository;
import com.dantn.weblaptop.mapper.DotGiamGiaMapper;
import com.dantn.weblaptop.service.DotGiamGiaService;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.util.ConvertStringToArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class DotGiamGiaServiceImpl implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepository dotGiamGiaRepository;
    @Autowired
    private DotGiamGiaMapper dotGiamGiaMapper;
    @Autowired
    private DotGiamGiaSpecifications dotGiamGiaSpec ;

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
    public Page<DotGiamGiaResponse> filterDotGiamGia(FilterDotGiamGia filterDotGiamGia, Integer page, Integer size) {
        Specification<DotGiamGia> spec = Specification
                .where(dotGiamGiaSpec.listStringEquals("ma", ConvertStringToArray.toArray(filterDotGiamGia.getMa())))
                .and(dotGiamGiaSpec.listStringLike("ten",ConvertStringToArray.toArray(filterDotGiamGia.getTen())))
                .and(dotGiamGiaSpec.listIntegerEquals("trangThai",ConvertStringToArray.toArray(filterDotGiamGia.getTrangThai())))
                .and(dotGiamGiaSpec.listIntegerEquals("giaTriGiam",ConvertStringToArray.toArray(filterDotGiamGia.getGiaTri())))
                .and(dotGiamGiaSpec.integerLessThanOrEquals("giaTriGiam",filterDotGiamGia.getGiaTriNhoHon()))
                .and(dotGiamGiaSpec.integerGreaterThanOrEquals("giaTriGiam",filterDotGiamGia.getGiaTriLonHon()))
                .and(dotGiamGiaSpec.localDateTimeBefore("thoiGianBatDau", filterDotGiamGia.getThoiGianBatDauTruoc()))
                .and(dotGiamGiaSpec.localDateTimeAfter("thoiGianBatDau", filterDotGiamGia.getThoiGianBatDauSau()))
                .and(dotGiamGiaSpec.localDateTimeBefore("thoiGianKetthuc", filterDotGiamGia.getThoiGianKetThucTruoc()))
                .and(dotGiamGiaSpec.localDateTimeAfter("thoiGianKetthuc", filterDotGiamGia.getThoiGianKetThucSau()));
        Pageable pageable  = PageRequest.of(page, size);
        Page<DotGiamGia>  dotGiamGias = dotGiamGiaRepository.findAll(spec,pageable);

        Page<DotGiamGiaResponse> responses = dotGiamGiaMapper.findAllRequestToDotGiamGiaResponse(dotGiamGias);
        return responses;
    }


}

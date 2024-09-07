package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.HinhThucThanhToanMapper;
import com.dantn.weblaptop.repository.HinhThucThanhToanRepository;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
import com.dantn.weblaptop.util.GenerateCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HinhThucThanhToanServiceImpl implements HinhThucThanhToanService {
    HinhThucThanhToanRepository paymentMethodRepository;
    @Override
    public ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<HinhThucThanhToan> paymentMethodsPage = paymentMethodRepository.findAll(pageable);
        Page<HinhThucThanhToanResponse> responses = paymentMethodsPage.map(
                paymentMethod -> HinhThucThanhToanMapper.toHinhThucThanhToanResponse(paymentMethod));
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
    public HinhThucThanhToanResponse create(CreateHinhThucThanhToanRequest request) {
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(
                HinhThucThanhToanMapper.create(request));
        HinhThucThanhToanResponse response = HinhThucThanhToanMapper.toHinhThucThanhToanResponse(paymentMethod);
        return response;
    }

    @Override
    public HinhThucThanhToanResponse update(Long id, UpdateHinhThucThanhToanRequest request) throws AppException {
        HinhThucThanhToan existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        HinhThucThanhToanMapper.update(request, existingPaymentMethod);
        HinhThucThanhToanResponse response = HinhThucThanhToanMapper.toHinhThucThanhToanResponse(
                paymentMethodRepository.save(existingPaymentMethod));
        return response;
    }



    @Override
    public void updateStart(Long id) {

    }
}

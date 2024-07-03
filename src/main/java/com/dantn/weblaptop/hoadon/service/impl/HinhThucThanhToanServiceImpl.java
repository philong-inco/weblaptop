package com.dantn.weblaptop.hoadon.service.impl;

import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.hoadon.dto.request.HinhThucThanhToanRequest;
import com.dantn.weblaptop.hoadon.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.hoadon.dto.response.Meta;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;
import com.dantn.weblaptop.hoadon.repository.HinhThucThanhToanRepository;
import com.dantn.weblaptop.hoadon.service.HinhThucThanhToanService;
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

    GenerateCode generateCode;

    @Override
    public ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<HinhThucThanhToanResponse> paymentMethodsPage = paymentMethodRepository.getAll(pageable);

        Meta meta = Meta.builder()
                .page(paymentMethodsPage.getNumber())
                .pageSize(paymentMethodsPage.getSize())
                .pages(paymentMethodsPage.getTotalPages())
                .total(paymentMethodsPage.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(paymentMethodsPage.getContent())
                .build();

        return response;
    }

    @Override
    public HinhThucThanhToan createPaymentMethods(HinhThucThanhToanRequest request) {
        HinhThucThanhToan newPaymentMethod = new HinhThucThanhToan();
        newPaymentMethod.setMa(generateCode.generateCode(GenerateCode.PAYMENT_METHOD_PREFIX));
        newPaymentMethod.setMoTa(request.getMoTa());
        newPaymentMethod.setTrangThai(0); // 0 HD , 1 huy
        newPaymentMethod.setTen(request.getTen());
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(newPaymentMethod);
        return paymentMethod;
    }

    @Override
    public HinhThucThanhToan updatePaymentMethods(Long id, HinhThucThanhToanRequest request) throws AppException {
        HinhThucThanhToan existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                ()-> new AppException("Cant not find payment method with ID "+id));
        existingPaymentMethod.setMa(generateCode.generateCode(GenerateCode.PAYMENT_METHOD_PREFIX));
        existingPaymentMethod.setMoTa(request.getMoTa());
        existingPaymentMethod.setTrangThai(request.getTrangThai()); // 0 HD , 1 huy
        existingPaymentMethod.setTen(request.getTen());
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(existingPaymentMethod);
        return paymentMethod;
    }

    @Override
    public Boolean updateStatus(Long id) {
        return null;
    }
}

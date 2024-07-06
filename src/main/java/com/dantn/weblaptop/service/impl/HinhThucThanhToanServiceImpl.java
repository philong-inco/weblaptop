package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.exception.AppException;
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
    public HinhThucThanhToan createPaymentMethods(CreateHinhThucThanhToanRequest request) {
        HinhThucThanhToan newPaymentMethod = new HinhThucThanhToan();
        newPaymentMethod.setMa(GenerateCode.generateHHTT());
        newPaymentMethod.setMoTa(request.getMoTa());
        newPaymentMethod.setTrangThai(0); // 0 HD , 1 huy
        newPaymentMethod.setTen(request.getTen());
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(newPaymentMethod);
        return paymentMethod;

    }

    @Override
    public HinhThucThanhToan updatePaymentMethods(Long id, UpdateHinhThucThanhToanRequest request) throws AppException {
        HinhThucThanhToan existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new AppException("Cant not find payment method with ID " + id));
        existingPaymentMethod.setMoTa(request.getMoTa());
        existingPaymentMethod.setTen(request.getTen());
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(existingPaymentMethod);
        return paymentMethod;
    }

    @Override
    public Boolean updateStatus(Long id) {
        Optional<HinhThucThanhToan> optional = paymentMethodRepository.findById(id);
        if (optional.isPresent()) {
            paymentMethodRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/payment-methods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HinhThucThanhToanController {

    HinhThucThanhToanService paymentMethodService;
    @GetMapping
    public ResponseEntity<ApiResponse> getPaymentMethodPage(
            @RequestParam(name = "page", defaultValue = "0") Optional<String> page,
            @RequestParam(name = "size", defaultValue = "5") Optional<String> size
    ) {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(paymentMethodService.getPaymentMethodsPage(page, size))
                .message("Success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createPaymentMethod(
            @Valid @RequestBody CreateHinhThucThanhToanRequest request
    ) {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(paymentMethodService.create(request))
                .message("Success")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updatePaymentMethod(
            @PathVariable  Long id,
            @Valid @RequestBody UpdateHinhThucThanhToanRequest request
    ) throws AppException {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(paymentMethodService.update(id, request))
                .message("Success")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonClientRequest;
import com.dantn.weblaptop.dto.request.create_request.GioHangChiTietRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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
            @PathVariable Long id,
            @Valid @RequestBody UpdateHinhThucThanhToanRequest request
    ) throws AppException {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(paymentMethodService.update(id, request))
                .message("Success")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("client/vn-pay")
    public ResponseEntity<ApiResponse> clientVnPay(
            @RequestBody
            @Valid List<GioHangChiTietRequest> cartDetails,
            HttpServletRequest request) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success")
                        .data(paymentMethodService.payWithVNPAYOnline(cartDetails, request))
                        .build()
        );
    }

    @PostMapping("admin/vn-pay/{billCode}")
    public ResponseEntity<ApiResponse> adminVnPay(
            @PathVariable String billCode,
            HttpServletRequest request) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success")
                        .data(paymentMethodService.payWithVNPAYOnlineAdmin(billCode, request))
                        .build()
        );
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
       this.paymentMethodService.handlePaymentCallback(request, response);
    }

    @GetMapping("/vn-pay-callback-2")
    public ResponseEntity<ApiResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        boolean isSuccess = "00".equals(status);
        return ResponseEntity.status(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .statusCode(isSuccess ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value())
                        .data(status)
                        .message(isSuccess ? "Success" : "Error")
                        .build()
        );
    }

}

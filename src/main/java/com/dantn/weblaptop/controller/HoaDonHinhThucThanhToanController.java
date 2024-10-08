package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HoaDonHinhThucThanhToanSerive;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hoa-don-httt")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonHinhThucThanhToanController {

    HoaDonHinhThucThanhToanSerive hoaDonHinhThucThanhToanSerive;

    @GetMapping("all/bill/{billCode}")
    public ResponseEntity<ApiResponse> getAllByBillCode(@PathVariable("billCode") String billCode) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Lich sủ thanh toán")
                        .statusCode(HttpStatus.OK.value())
                        .data(hoaDonHinhThucThanhToanSerive.getAllByBillCode(billCode))
                        .build()
        );
    }

    @PostMapping("create/bill/{billCode}")
    public ResponseEntity<ApiResponse> createPaymentHistory(
            @RequestBody @Valid CreateHoaDonHinhThucThanhToanRequest request,
            @PathVariable(name = "billCode") String billCode
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .message("Tạo mới thành công")
                .statusCode(HttpStatus.CREATED.value())
                .data(hoaDonHinhThucThanhToanSerive.create(request, billCode))
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteHoaDonHinhThucThanhToan(
            @PathVariable(name = "id") Long id
    ) throws AppException {
        hoaDonHinhThucThanhToanSerive.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                .message("Tạo mới thành công")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build());
    }
}

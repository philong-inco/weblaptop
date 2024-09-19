package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.BanHangService;
import com.dantn.weblaptop.service.HoaDonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sell/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BanHangController {
    BanHangService banHangService;

    HoaDonService billService;

    @GetMapping("/bill-codes")
    public ApiResponse<Object> getBillCodes() {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get Bill Codes")
                .data(banHangService.getAllBillCode())
                .build();
    }

    @DeleteMapping("/delete/{billCode}")
    public ResponseEntity<ApiResponse> deleteBillByCode(
            @PathVariable(name = "billCode") String billCode
    ) {
        billService.deleteBillByCode(billCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("Delete Bill Code Success")
                        .build()
        );
    }
}

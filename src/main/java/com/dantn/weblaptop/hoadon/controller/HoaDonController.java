package com.dantn.weblaptop.hoadon.controller;

import com.dantn.weblaptop.hoadon.dto.request.HoaDonRequest;
import com.dantn.weblaptop.hoadon.dto.response.ApiResponse;
import com.dantn.weblaptop.hoadon.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.hoadon.service.LichSuHoaDonService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/bills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonController {
    LichSuHoaDonService lichSuHoaDonService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createBill(@Valid @RequestBody HoaDonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateBillById(@Valid @RequestBody HoaDonRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/bill-history/{billId}")
    public ResponseEntity<ApiResponse> getBillHistoryByBillId(
            @PathVariable Long billId,
            @RequestParam(name = "page", defaultValue = "0") Optional<String> page,
            @RequestParam(name = "size", defaultValue = "5") Optional<String> size
    ) {
        ResultPaginationResponse response = lichSuHoaDonService.getBillHistoryByBillId(billId, page, size);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Call api success")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}

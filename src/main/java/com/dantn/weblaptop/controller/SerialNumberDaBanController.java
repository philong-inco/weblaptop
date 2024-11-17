package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.SerialNumberDaBan_Dto;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberCodeDaBanRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/serial-number-sold")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberDaBanController {
    SerialNumberDaBanService serialNumberDaBanService;
    @GetMapping
    public ResponseEntity<ApiResponse> getSerialNumberSold(
            @RequestParam(name = "code") String billCode) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get product list and serial number sold by bill code")
                        .data(serialNumberDaBanService.getSerialNumberDaBanPage(billCode))
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(
            @RequestBody CreateSerialNumberDaBanRequest request) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Created successfully")
                        .data(serialNumberDaBanService.create(request))
                        .build());
    }

    @PostMapping("create-by-product-code")
    public ResponseEntity<ApiResponse> createByProductCode(
            @RequestBody CreateSerialNumberCodeDaBanRequest request) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Created successfully")
                        .data(serialNumberDaBanService.createByProductCode(request))
                        .build());
    }

    @PostMapping("delete")
    public ResponseEntity<ApiResponse> delete(
            @RequestBody SerialNumberSoldDelete request) throws AppException {
        serialNumberDaBanService.delete(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Deleted successfully")
                        .build());
    }

    @GetMapping("topsold")
    public ResponseEntity<ApiResponse> topSold(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) throws AppException {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Top sold items retrieved successfully")
                        .data(serialNumberDaBanService.findSerialNumberDaBanTopSold(startDate, endDate))
                        .build());
    }

}

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateSerialNumberDaBanRequest;
import com.dantn.weblaptop.dto.request.update_request.SerialNumberSoldDelete;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/serial-number-sold")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SerialNumberDaBanController {
    SerialNumberDaBanService serialNumberDaBanService;

    @GetMapping
    public ResponseEntity<ApiResponse> getSerialNumberSold(
            @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get product list and serial number sold by bill code")
                        .data(serialNumberDaBanService.getSerialNumberDaBanPage(code))
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

    @DeleteMapping("delete")
    public ResponseEntity<ApiResponse> delete(@RequestBody SerialNumberSoldDelete request) throws AppException {
        serialNumberDaBanService.delete(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Deleted successfully")
                        .build());
    }
}

<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/controller/HinhThucThanToanController.java
package com.dantn.weblaptop.hoadon.controller;

import com.dantn.weblaptop.hoadon.dto.request.HinhThucThanhToanRequest;
import com.dantn.weblaptop.hoadon.dto.response.ApiResponse;
import com.dantn.weblaptop.hoadon.exception.AppException;
import com.dantn.weblaptop.hoadon.service.HinhThucThanhToanService;
=======
package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
>>>>>>> main:src/main/java/com/dantn/weblaptop/controller/HinhThucThanToanController.java
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/payment-methods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HinhThucThanToanController {
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
<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/controller/HinhThucThanToanController.java
            @Valid @RequestBody HinhThucThanhToanRequest request
=======
            @Valid @RequestBody CreateHinhThucThanhToanRequest request
>>>>>>> main:src/main/java/com/dantn/weblaptop/controller/HinhThucThanToanController.java
    ) {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(paymentMethodService.createPaymentMethods(request))
                .message("Success")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updatePaymentMethod(
<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/controller/HinhThucThanToanController.java
          @PathVariable  Long id,
            @Valid @RequestBody HinhThucThanhToanRequest request
=======
            @PathVariable  Long id,
            @Valid @RequestBody UpdateHinhThucThanhToanRequest request
>>>>>>> main:src/main/java/com/dantn/weblaptop/controller/HinhThucThanToanController.java
    ) throws AppException {
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(paymentMethodService.updatePaymentMethods(id, request))
                .message("Success")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/controller/HinhThucThanToanController.java
=======

>>>>>>> main:src/main/java/com/dantn/weblaptop/controller/HinhThucThanToanController.java

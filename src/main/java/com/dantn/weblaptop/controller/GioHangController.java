package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.AddToGioHangRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.GioHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GioHangController {
    GioHangService gioHangService;

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addCart(@RequestBody AddToGioHangRequest listAddToCart) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .data(gioHangService.addToCart(listAddToCart))
                        .message("Thêm sản phẩm vào giỏ hàng thành công")
                        .build()
        );
    }

    @GetMapping("/{idKhachHang}")
    public ResponseEntity<ApiResponse> getListCart(@PathVariable("idKhachHang") Long idKhachHang) throws AppException {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(gioHangService.getListCart(idKhachHang))
                        .message("Lấy thành công dach sách giỏ hàng chi tiết")
                        .build()
        );
    }

    @GetMapping("/quantityInCart/{idKhachHang}")
    public ResponseEntity<ApiResponse> getQuantityInCart(@PathVariable("idKhachHang") Long idKhachHang) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(gioHangService.quantityInCart(idKhachHang))
                        .message("Lấy thành số lượng")
                        .build()
        );
    }

}

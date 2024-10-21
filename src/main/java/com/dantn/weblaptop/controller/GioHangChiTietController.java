package com.dantn.weblaptop.controller;


import com.dantn.weblaptop.dto.request.update_request.UpdateSoLongRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.GioHangChiTietService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart-detail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GioHangChiTietController {

    GioHangChiTietService gioHangChiTietService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCartDetail(@PathVariable("id") Long idCartDetail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .data(gioHangChiTietService.deleteCartDetail(idCartDetail))
                        .message("Xóa thành công")
                        .build()
        );
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<ApiResponse> changeQuantity(@RequestBody @Valid UpdateSoLongRequest request) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .data(gioHangChiTietService.changeQuantity(request))
                        .message("Thay đổi số lượng thành công")
                        .build()
        );
    }

    @DeleteMapping("/deleteAll/")
    public ResponseEntity<ApiResponse> deleteAllCart(
            @RequestParam(name = "idKhachHang", defaultValue = "0") Long idKhachHang,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(gioHangChiTietService.deleteAllCart(idKhachHang, httpServletRequest))
                        .message("Xóa tất cả giỏ hàng chi tiết thành công")
                        .build()
        );
    }
}

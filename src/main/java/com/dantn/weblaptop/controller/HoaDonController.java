package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.service.impl.HoaDonServiceImpl;
import com.turkraft.springfilter.boot.Filter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/bills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonController {

    LichSuHoaDonService billHistoryService;
    HoaDonService billService;
    HoaDonServiceImpl hoaDonService;

    // code test 1 2 3
    @GetMapping("test/{code}")
    public ResponseEntity<ApiResponse> a(@PathVariable(name = "code") String code) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder().statusCode(HttpStatus.OK.value())
                .data(hoaDonService.prepareTheBill(code)).build());
    }


    @GetMapping("all")
    public ResponseEntity<ApiResponse> filterBill(
            @Filter Specification<HoaDon> specification,
            Pageable pageable
    ) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Oke");
        apiResponse.setData(billService.filterHoaDon(specification, pageable));
        return ResponseEntity.ok(apiResponse);
    }

    // Lấy thông tin hd thoe ID
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getBillPage(
            @PathVariable(name = "id") Long id
    ) throws AppException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Thông tin hóa đơn");
        apiResponse.setData(billService.getBillById(id));
        return ResponseEntity.ok(apiResponse);
    }

    //    Lấy thông tin hóa đơn vói mã
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse> getBillPageByCode(
            @PathVariable(name = "code") String code
    ) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Lấy thông tin hóa đơn theo mã")
                .data(billService.getBillByCode(code)).build());
    }


    @PostMapping("create")
    public ResponseEntity<ApiResponse> createBill() throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Tạo mới hóa đơn thành công")
                        .data(billService.createBill())
                        .build()
        );
    }

    @PostMapping("add-customer-to-bill")
    public ResponseEntity<ApiResponse> addCustomerToBill(
            @RequestParam(name = "customerId") Long customerId,
            @RequestParam(name = "codeBill") String codeBill
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add customer to bill success")
                        .data(billService.addCustomerToBill(customerId, codeBill))
                        .build()
        );
    }

    @PostMapping("add-coupon-by-id")
    public ResponseEntity<ApiResponse> addCouponToBill(
            @RequestParam(name = "couponId") Long couponId,
            @RequestParam(name = "codeBill") String codeBill
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add coupon to bill success")
                        .data(billService.addCouponToBill(couponId, codeBill))
                        .build()
        );
    }

    @PostMapping("add-coupon-by-code")
    public ResponseEntity<ApiResponse> addCouponToBillByCouponCode(
            @RequestParam(name = "couponCode") String couponCode,
            @RequestParam(name = "codeBill") String codeBill
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Add coupon to bill success")
                        .data(billService.addCouponToBillByCode(couponCode, codeBill))
                        .build()
        );
    }

    @PostMapping("pay-counter/{billCode}")
    public ResponseEntity<ApiResponse> payCounter(
            @PathVariable(name = "billCode") String billCode
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Pay counter success")
                        .data(billService.payCounter(billCode))
                        .build()
        );
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateBillById(@RequestBody UpdateHoaDonRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/bill-history/{code}")
    public ResponseEntity<ApiResponse> getBillHistoryByBillCode(
            @PathVariable String code
    ) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Call api success")
                .data(billHistoryService.getBillHistoryByBillCode(code))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getBillByStatusAndType(
            @RequestParam(name = "status") String status,
            @RequestParam(name = "type") Integer type,
            Optional<String> page, Optional<String> size
    ) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Call api success")
                .data(billService.pageBillByStatusAndType(status, type, page, size))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    /// Lôi FE chưa lấy đc code
    /// thay lại phương thức bên FE
    @PostMapping("update-status/{code}")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable(name = "code") String code,
            @RequestParam(name = "status") String status
    ) throws AppException {
        billService.updateStatus(code, status);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/bill-history/{code}/revert-status")
    public ResponseEntity<ApiResponse> revertBillStatus(
            @PathVariable String code) throws AppException {
        billHistoryService.revertBillStatus(code);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Chuyển đổi trạng thái thành công")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    // bill detail

}

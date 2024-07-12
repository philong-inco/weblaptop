package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
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
@RequestMapping("api/v1/bills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonController {

    LichSuHoaDonService billHistoryService;
    HoaDonService billService;


    @GetMapping("all")
    public ResponseEntity<ApiResponse> filterBill(
            @Filter Specification<HoaDon> specification,
            Pageable pageable
    ) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Oke");
        apiResponse.setData(billService.filterHoaDon(specification , pageable));
        return ResponseEntity.ok(apiResponse);
    }

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

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createBill() throws AppException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.CREATED.value());
        apiResponse.setMessage("Tạo mới hóa đơn thành công");
        apiResponse.setData(billService.createBill());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateBillById(@RequestBody UpdateHoaDonRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/bill-history/{billId}")
    public ResponseEntity<ApiResponse> getBillHistoryByBillId(
            @PathVariable Long billId
    ) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.OK.value())
                .message("Call api success")
                .data(billHistoryService.getBillHistoryByBillId(billId))
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
    @DeleteMapping("update-status/{id}")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "status") String status
    ) throws AppException {
        billService.updateStatus(id, status);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // bill detail

}

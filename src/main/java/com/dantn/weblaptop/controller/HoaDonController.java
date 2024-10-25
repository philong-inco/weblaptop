package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonClientAccountRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateHoaDonClientRequest;
import com.dantn.weblaptop.dto.request.create_request.CreateLichSuHoaDon;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChiHoaDonRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHoaDonRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.LichSuHoaDonService;
import com.dantn.weblaptop.service.impl.HoaDonServiceImpl;
import com.dantn.weblaptop.util.SendEmailBill;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonController {

    LichSuHoaDonService billHistoryService;
    HoaDonService billService;
    HoaDonServiceImpl hoaDonService;
    SendEmailBill sendEmailBill;

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

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportHoaDonToExcel(
            @Filter Specification<HoaDon> specification) throws IOException {
        byte[] excelData = hoaDonService.export(specification);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hoadon_" + LocalDateTime.now().toLocalTime() + ".xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
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
            @PathVariable(name = "billCode") String billCode,
            @RequestBody UpdateHoaDonRequest request
    ) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Pay counter success")
                        .data(billService.payCounter(billCode, request))
                        .build()
        );
    }

    @PostMapping("update-address-in-bill/{billCode}")
    public ResponseEntity<ApiResponse> updateAddressInBill(
            @PathVariable(name = "billCode") String billCode,
            @RequestBody @Valid UpdateDiaChiHoaDonRequest request) throws AppException {
        billService.updateAddressInBill(billCode, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Cập nhập địa chỉ đơn hàng thành công")
                        .build()
        );
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
            @RequestParam(name = "status") String status,
            @RequestBody @Valid CreateLichSuHoaDon request
    ) throws AppException {
        billService.updateStatus(code, status, request);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Cập nhập hóa đơn thành công")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("change-status/{code}")
    public ResponseEntity<ApiResponse> changeStatus(
            @PathVariable(name = "code") String code,
            @RequestParam(name = "status") String status
    ) throws AppException {
        billService.changeStatus(code, status);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Cập nhập hóa đơn thành công")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("list-hang-bill")
    public ResponseEntity<ApiResponse> getHangBill() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(billService.listHangBill())
                        .message("Danh sách hóa đơn treo")
                        .build()
        );
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

    @GetMapping("/countbill")
    public ResponseEntity<ApiResponse> getCountBill(@RequestParam("startDate") Long startDate, @RequestParam("endDate") Long endDate) {
        try {

            Long count = hoaDonService.countBillByDate(startDate, endDate);

            // Create a successful ApiResponse
            ApiResponse response = new ApiResponse("Số lượng hóa đơn:", count);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // Handle exceptions and create an error ApiResponse
            ApiResponse errorResponse = new ApiResponse(false, "Đã xảy ra lỗi: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/sumpricebill")
    public ResponseEntity<ApiResponse> sumPriceBill(@RequestParam("startDate") Long startDate, @RequestParam("endDate") Long endDate) {
        try {
            BigDecimal sum = hoaDonService.sumBillByDate(startDate, endDate);
            ApiResponse response = new ApiResponse("Tổng tiền hóa đơn:", sum);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(false, "Đã xảy ra lỗi: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/bill-pdf/{billCode}")
    public ResponseEntity<byte[]> getBillPdf(
            @PathVariable(name = "billCode") String billCode
    ) {
        try {
            // chyền data
            byte[] pdfBytes = billService.getInvoicePdf(billCode);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", billCode + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/order-pdf/{billCode}")
    public ResponseEntity<byte[]> getOrderPdf(
            @PathVariable(name = "billCode") String billCode
    ) {
        try {
            // chyền data
            byte[] pdfBytes = billService.getInvoicePdf(billCode);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "invoice.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("client/create-bill")
    public ResponseEntity<ApiResponse> createBillClient(
            @RequestBody @Valid CreateHoaDonClientRequest request
    ) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(hoaDonService.createBillClient(request))
//                        .data(request)
                .statusCode(HttpStatus.CREATED.value())
                .build());
    }

    @PostMapping("client/create-bill/account")
    public ResponseEntity<ApiResponse> createBillClientAccount(
            @RequestBody @Valid CreateHoaDonClientAccountRequest  request
    ) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(hoaDonService.createBillClientAccount(request))
//                        .data(request)
                .statusCode(HttpStatus.CREATED.value())
                .build());
    }

    @GetMapping("client/look-up-orders")
    public ResponseEntity<ApiResponse> lookUpOrders(
            @RequestParam(name = "billCode") String billCode,
            @RequestParam(name = "phoneNumber") String phoneNumber
    ) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(hoaDonService.lookUpOrders(billCode, phoneNumber))
                .statusCode(HttpStatus.CREATED.value())
                .build());
    }

    @GetMapping("client/get-all-bills")
    public ResponseEntity<ApiResponse> lookUpOrders(
            @RequestParam(name = "idKhachHang") Long idKhachHang,
            @RequestParam(name = "trangThai") String status
    ) throws AppException {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(hoaDonService.getAllByCustomerIdAndStatus(idKhachHang, status))
                .statusCode(HttpStatus.CREATED.value())
                .build());
    }
}


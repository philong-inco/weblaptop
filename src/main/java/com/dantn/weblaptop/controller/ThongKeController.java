package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.FindBillDateRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/statistical")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongKeController {

    @GetMapping("/day")
    public ResponseEntity<ApiResponse> statisticalDay() {
        return  null;
    }

    @GetMapping("/month")
    public ResponseEntity<ApiResponse>  statisticalMonth() {
        return null;
    }

    @GetMapping("/status-bill")
    public ResponseEntity<ApiResponse>  statisticalStatusBill(final FindBillDateRequest req) {
        return null;
    }
    @GetMapping("/best-selling-product")
    public ResponseEntity<ApiResponse>  statisticalBestSellingProduct(final FindBillDateRequest req) {
        return null;
    }
    @GetMapping("/bill-date")
    public ResponseEntity<?> statisticalBillDate(final FindBillDateRequest req) {
       return null;
    }

    @GetMapping("/growth")
    public ResponseEntity<?> statisticalGrowth() {


        return null;
    }
}

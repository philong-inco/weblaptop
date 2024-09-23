package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdatePhieuGiamGiaRequest;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.PhieuGiamGiaResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.repository.HoaDonRepository;
import com.dantn.weblaptop.service.impl.PhieuGiamGiaService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
public class PhieuGiamGiaController {
    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> filterCoupons(
            @Filter Specification<PhieuGiamGia> specification,
            Pageable pageable
    ) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Call api success");
        apiResponse.setData(phieuGiamGiaService.filterCoupons(specification, pageable));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPhieuGiamGia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Call api success");
        apiResponse.setData(phieuGiamGiaService.getAll(page, size));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addPhieuGiamGia(@Valid @RequestBody CreatePhieuGiamGiaRequest request) throws AppException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.CREATED.value());
        apiResponse.setMessage("Create success");
        apiResponse.setData(phieuGiamGiaService.add(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePhieuGiamGia(@Valid @RequestBody UpdatePhieuGiamGiaRequest request, @PathVariable Long id) throws AppException, MessagingException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Update success");
        apiResponse.setData(phieuGiamGiaService.update(request, id));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public PhieuGiamGia deletePhieuGiamGia(@PathVariable Long id) {
        return phieuGiamGiaService.delete(id);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse> detailPhieuGiamGia(@PathVariable Long id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Call Api by id");
        apiResponse.setData(phieuGiamGiaService.detail(id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("customer-coupons/{id}")
    public ResponseEntity<ApiResponse> getPageKhachHangPhieuGiamGia(
            @PathVariable(name = "id") Long id,
            @RequestParam Optional<String> page,
            @RequestParam Optional<String> size
    ) throws AppException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Call Api ustomer-coupons by id");
        apiResponse.setData(phieuGiamGiaService.getKhPGGById(id, page, size));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("customer-coupons/del/{id}")
    public ResponseEntity<Void> updateTrangThaiKHPGG(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "status") Integer status
    ) throws AppException {
        phieuGiamGiaService.updateStatusKhachHangPhieuGiamGia(id, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("to-bill/{billCode}")
    public ResponseEntity<ApiResponse> getAllCouponsToBill(
            @PathVariable(name = "billCode") String billCode
    ) {
        Optional<HoaDon> optional = hoaDonRepository.findHoaDonByMa(billCode);
        List<PhieuGiamGiaResponse> result = new ArrayList<>();
        if (optional.isPresent()) {
            if (optional.get().getKhachHang() == null) {
                result = phieuGiamGiaService.getAllByTotalAmount(optional.get().getTongTienBanDau());
            } else {
                result = phieuGiamGiaService
                        .getAllByTotalAmountAndCustomer(
                                optional.get().getTongTienBanDau(),
                                optional.get().getKhachHang().getId());
            }
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all coupons to bill success")
                        .data(result)
                        .build()
        );
    }
}

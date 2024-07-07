package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.service.impl.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
public class PhieuGiamGiaController {
    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;

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
    public PhieuGiamGia addPhieuGiamGia(@RequestBody PhieuGiamGia phieuGiamGia) {
        return phieuGiamGiaService.add(phieuGiamGia);
    }

    @PutMapping("/update/{id}")
    public PhieuGiamGia updatePhieuGiamGia(@RequestBody PhieuGiamGia phieuGiamGia, @PathVariable Long id) {
        return phieuGiamGiaService.update(phieuGiamGia, id);
    }

    @DeleteMapping("/delete/{id}")
    public PhieuGiamGia deletePhieuGiamGia(@PathVariable Long id) {
        return phieuGiamGiaService.delete(id);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse> detailPhieuGiamGia(@PathVariable Long id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Call Api by id");
        apiResponse.setData(phieuGiamGiaService.detail(id));
        return ResponseEntity.ok(apiResponse) ;
    }

}

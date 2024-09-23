package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.DotGiamGiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/discounts")
public class DotGiamGiaController {
    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    @GetMapping("/{id}")
    public DotGiamGiaResponse getOneDotGiamGia(@PathVariable Long id) {
        return dotGiamGiaService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CreateDotGiamGiaRequest request, BindingResult bindingResult) throws AppException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        DotGiamGiaResponse response = dotGiamGiaService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateGotGiamGiaRequest request, @PathVariable Long id, BindingResult bindingResult) throws AppException {
        if (bindingResult.hasErrors()) {
            // Xử lý lỗi validation ở đây (nếu cần)
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        DotGiamGiaResponse response = dotGiamGiaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> filterDotGiamGia(
            @RequestParam(value = "tenOrMa", required = false, defaultValue = "") String tenOrMa,
            @RequestParam(value = "giaTri", required = false, defaultValue = "") String giaTriStr,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThaiStr,
            @RequestParam(value = "ngayBatDau", required = false, defaultValue = "") String ngayBatDauStr,
            @RequestParam(value = "ngayKetThuc", required = false, defaultValue = "") String ngayKetThucStr,
            @RequestParam(value = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(value = "size", required = false, defaultValue = "4") String sizeStr
    ) {
        Integer page, size, giaTri, trangThai;
        LocalDateTime start = null, end = null;
        try {
            page = Integer.valueOf(pageStr);
            size = Integer.valueOf(sizeStr);
        } catch (NumberFormatException e) {
            page = 0;
            size = 4;
        }
        try {
            giaTri = giaTriStr.isEmpty() ? null : Integer.valueOf(giaTriStr);
            trangThai = trangThaiStr.isEmpty() ? null : Integer.valueOf(trangThaiStr);
        } catch (NumberFormatException e) {
            giaTri = null;
            trangThai = null;
        }

        try {
            start = ngayBatDauStr.isEmpty() ? null : LocalDateTime.parse(ngayBatDauStr);
        } catch (Exception e) {
            start = null;
        }

        try {
            end = ngayKetThucStr.isEmpty() ? null : LocalDateTime.parse(ngayKetThucStr);
        } catch (Exception e) {
            end = null;
        }
        return ResponseEntity.ok(dotGiamGiaService.filter(tenOrMa, giaTri, trangThai, start, end, page, size));
    }

    @PutMapping("/changestatus/{id}")
    public ResponseEntity<?> changeStatusDotGiamGia(@PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Xử lý lỗi validation ở đây (nếu cần)
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        dotGiamGiaService.changeStatusDotGiamGia(id);
        return ResponseEntity.ok("Bạn đã update trạng thái thành công");
    }

}

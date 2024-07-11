package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.create_request.FilterDotGiamGia;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.service.DotGiamGiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discounts")
public class DotGiamGiaController {
    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    @GetMapping("/all")
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "4", required = false) int size) {
        return ResponseEntity.ok().body(dotGiamGiaService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public DotGiamGiaResponse getOneDotGiamGia(@PathVariable Long id) {
        return dotGiamGiaService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CreateDotGiamGiaRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Xử lý lỗi validation ở đây (nếu cần)
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        DotGiamGiaResponse response = dotGiamGiaService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateGotGiamGiaRequest request, @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Xử lý lỗi validation ở đây (nếu cần)
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        DotGiamGiaResponse response = dotGiamGiaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterDotGiamGia(
            @RequestParam(value = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(value = "ten", required = false, defaultValue = "") String ten,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "thoiGianBatDauTruoc", required = false, defaultValue = "") String thoiGianBatDauTruoc,
            @RequestParam(value = "thoiGianBatDauSau", required = false, defaultValue = "") String thoiGianBatDauSau,
            @RequestParam(value = "thoiGianKetThucTruoc", required = false, defaultValue = "") String thoiGianKetThucTruoc,
            @RequestParam(value = "thoiGianKetThucSau", required = false, defaultValue = "") String thoiGianKetThucSau,
            @RequestParam(value = "giaTri", required = false, defaultValue = "") String giaTri,
            @RequestParam(value = "giaTriNhoHon", required = false, defaultValue = "") String giaTriNhoHon,
            @RequestParam(value = "giaTriLonHon", required = false, defaultValue = "") String giaTriLonHon,
            @RequestParam(value = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(value = "size", required = false, defaultValue = "4") String sizeStr
    ) {
        FilterDotGiamGia filterDotGiamGia = FilterDotGiamGia.builder()
                .ma(ma)
                .ten(ten)
                .trangThai(trangThai)
                .giaTri(giaTri)
                .giaTriNhoHon(giaTriNhoHon)
                .giaTriLonHon(giaTriLonHon)
                .thoiGianBatDauSau(thoiGianBatDauSau)
                .thoiGianBatDauTruoc(thoiGianBatDauTruoc)
                .thoiGianKetThucSau(thoiGianKetThucSau)
                .thoiGianKetThucTruoc(thoiGianKetThucTruoc)
                .giaTriLonHon(giaTriLonHon)
                .giaTriNhoHon(giaTriNhoHon)
                .build();
        Integer page, size;
        try {
            page = Integer.valueOf(pageStr);
            size = Integer.valueOf(sizeStr);
        } catch (Exception e) {
            page = 0;
            size = 4;
        }
        return ResponseEntity.ok(dotGiamGiaService.filterDotGiamGia(filterDotGiamGia, page, size));
    }
}

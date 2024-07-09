package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateGotGiamGiaRequest;
import com.dantn.weblaptop.dto.response.DotGiamGiaResponse;
import com.dantn.weblaptop.service.DotGiamGiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = -1)
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

}

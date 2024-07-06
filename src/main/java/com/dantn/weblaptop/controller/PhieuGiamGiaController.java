package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import com.dantn.weblaptop.service.impl.PhieuGiamGiaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@AllArgsConstructor
public class PhieuGiamGiaController {

    private final PhieuGiamGiaService phieuGiamGiaService;

    @GetMapping
    public Page<PhieuGiamGia> getAllPhieuGiamGia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return phieuGiamGiaService.getAll(page, size);
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
    public PhieuGiamGia detailPhieuGiamGia(@PathVariable Long id){
        return phieuGiamGiaService.detail(id);
    }

}

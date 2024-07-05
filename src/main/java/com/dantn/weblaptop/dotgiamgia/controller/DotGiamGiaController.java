package com.dantn.weblaptop.dotgiamgia.controller;

import com.dantn.weblaptop.dotgiamgia.model.request.CreateDotGiamGiaRequest;
import com.dantn.weblaptop.dotgiamgia.service.DotGiamGiaService;
import com.dantn.weblaptop.dotgiamgia.model.request.UpdateDotGiamGiaRequest;
import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = -1)
@RequestMapping("/api/v1")
public class DotGiamGiaController {
    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    @GetMapping("/sales")
    public List<DotGiamGia> index() {
        return dotGiamGiaService.findAll();
    }

    @GetMapping("/sales/{id}")
    public DotGiamGia getOneDotGiamGia(@PathVariable Long id) {
        return dotGiamGiaService.findById(id);
    }

    @PostMapping("/create/sales")
    public void add(@RequestBody CreateDotGiamGiaRequest request) {
        dotGiamGiaService.save(request);
    }

    @PutMapping("/update/sales/{id}")
    public void suaDotGiamGia(@RequestBody UpdateDotGiamGiaRequest request, @PathVariable Long id) {
        dotGiamGiaService.update(id, request);
    }

    @DeleteMapping("/delete/sales/{id}")
    public void deleteDotGiamGia(@PathVariable Long id) {
        dotGiamGiaService.delete(id);
    }
}

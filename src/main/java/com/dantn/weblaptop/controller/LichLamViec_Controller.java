package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateLichLamViec;
import com.dantn.weblaptop.dto.request.update_request.UpdateLichLamViec;
import com.dantn.weblaptop.service.LichLamViec_Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichlamviec")
@Component
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class LichLamViec_Controller {
    private final LichLamViec_Service lichlamviec_service;

    @GetMapping("/danhsachlich")
    public ResponseEntity<?> danhsachlich() {
        return ResponseEntity.ok(lichlamviec_service.getAllLichLamViec());
    }

    @GetMapping("/danhsachlichnhanvien/{id}")
    public ResponseEntity<?> danhsachlichnhanvien(@RequestParam("id") Long id) {
        return ResponseEntity.ok(lichlamviec_service.getLichLamViecByIdNhanVien(id));
    }

    @PostMapping("/addlichnhanvien")
     public ResponseEntity<?> themlich(@Valid @RequestBody CreateLichLamViec lichlamviec, BindingResult result) {
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(lichlamviec_service.createLichLamViec(lichlamviec));
    }

    @PutMapping("/updatelichnhanvien/{id}")
    public ResponseEntity<?> updateLichNhanVien(@Valid @RequestBody UpdateLichLamViec updateLichLamViec, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(lichlamviec_service.updateLichLamViec(id,updateLichLamViec));
    }

    @PutMapping("/changestatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) {
        lichlamviec_service.deleteLichLamViec(id);
        return ResponseEntity.ok("Update status successful");
    }
}

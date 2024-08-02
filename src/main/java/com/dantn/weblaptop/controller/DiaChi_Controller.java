package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.service.DiaChi_Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diachi")
@AllArgsConstructor
@Component
public class DiaChi_Controller {
    @Qualifier("diaChi_Service")
    private final DiaChi_Service diaChiService;

    @PostMapping("create")
    public ResponseEntity<DiaChi_Response> createDiaChi(@RequestBody CreateDiaChi createDiaChi) {
        DiaChi_Response response = diaChiService.createDiaChi(createDiaChi);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/updatelocation/{id}")
    public ResponseEntity<DiaChi_Response> updateDiaChi(@PathVariable Long id, @RequestBody UpdateDiaChi updateDiaChi) {
        DiaChi_Response response = diaChiService.updateDiaChi(id, updateDiaChi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaChi_Response> getDiaChiById(@PathVariable Long id) {
        DiaChi_Response response = diaChiService.getDiaChiById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllDiaChiByIdKhachHang/{id}")
    public ResponseEntity<?> getAllDiaChiByIdKhachHang(@PathVariable("id") Long idKhachHang) {
        List<DiaChi_Response> diaChiPage = diaChiService.getAllDiaChiByIdKhachHang(idKhachHang);
        return new ResponseEntity<>(diaChiPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaChi(@PathVariable Long id) {
        diaChiService.deleteDiaChi(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

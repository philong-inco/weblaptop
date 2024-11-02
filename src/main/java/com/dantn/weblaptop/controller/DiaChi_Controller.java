package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateDiaChi;
import com.dantn.weblaptop.dto.request.update_request.UpdateDiaChi;
import com.dantn.weblaptop.dto.response.ApiResponse;
import com.dantn.weblaptop.dto.response.DiaChi_Response;
import com.dantn.weblaptop.entity.khachhang.DiaChi;
import com.dantn.weblaptop.repository.DiaChi_Repository;
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
    private final DiaChi_Repository diaChi_Repository;

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

    @GetMapping("/getDiaChiDefauldByIdKhachHang/{id}")
    public ResponseEntity<?> getDiaChiDefauldByIdKhachHang(@PathVariable("id") Long idKhachHang) {
        DiaChi_Response diaChiDefauldOfIdKhachHang = diaChiService.getDiaChiDefauldOfIdKhachHang(idKhachHang);
        return new ResponseEntity<>(diaChiDefauldOfIdKhachHang, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaChi(@PathVariable Long id) {
        diaChiService.deleteDiaChi(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/defaultlocation/{id}")
    public ResponseEntity<String> defaultLocation(@PathVariable Long id, @RequestParam Long idKhachHang) {
        try {
            diaChiService.defauldiaChi(id, idKhachHang);
            // Trả về thông báo thành công
            return ResponseEntity.ok("Đặt địa chỉ mặc định thành công");
        } catch (Exception e) {
            // Trả về thông báo thất bại
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đặt địa chỉ mặc định thất bại: " + e.getMessage());
        }
    }



    @PutMapping("/undefaultlocation/{id}")
    public ResponseEntity<?> unDefaultLocation(@PathVariable Long id, @RequestParam Long idKhachHang) {
        diaChiService.unDefauldiaChi(id, idKhachHang);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

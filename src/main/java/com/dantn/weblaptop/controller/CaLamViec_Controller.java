package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.CaLamViec_Response;
import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.service.CaLamViecService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ca_lam_viec")
@RestController
@Component
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CaLamViec_Controller {
    private final CaLamViecService caLamViecService;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAllCaLamViec() {
        List<CaLamViec_Response> caLamViecs = caLamViecService.getListCaLamViec();
        return ResponseEntity.ok(caLamViecs);
    }

    @GetMapping("/searchcalamviec/{id}")
    public ResponseEntity<?> findCaLamViec(@PathVariable Long id) {
        CaLamViec_Response caLamViec = caLamViecService.findByIdCaLamViec(id);
        return ResponseEntity.ok(caLamViec);
    }
}

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.CreateVaiTro_Request;
import com.dantn.weblaptop.dto.request.update_request.UpdateVaiTro_Request;
import com.dantn.weblaptop.dto.response.VaiTro_Response;
import com.dantn.weblaptop.service.VaiTro_Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro")
@AllArgsConstructor
@Component
@CrossOrigin(origins = "http://localhost:3000")
public class VaiTro_Controller {

    @Qualifier("vaiTro_Service")
    private final VaiTro_Service vaiTroService;

    @PostMapping("/create")
    public ResponseEntity<VaiTro_Response> createVaiTro(@Valid @RequestBody CreateVaiTro_Request request) {
        VaiTro_Response response = vaiTroService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VaiTro_Response> updateVaiTro(@PathVariable Long id, @Valid @RequestBody UpdateVaiTro_Request request) {
        VaiTro_Response response = vaiTroService.update(request, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaiTro_Response> getVaiTroById(@PathVariable Long id) {
        VaiTro_Response response = vaiTroService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VaiTro_Response>> getAllVaiTros() {
        List<VaiTro_Response> responses = vaiTroService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<VaiTro_Response>> pageAllVaiTros(Pageable pageable) {
        Page<VaiTro_Response> responses = vaiTroService.pageAllVaiTro(pageable)
                .map(vaiTro -> vaiTroService.getById(vaiTro.getId()));
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/revert-status/{id}")
    public ResponseEntity<Void> revertStatus(@PathVariable Long id) {
        vaiTroService.revertStatus(id);
        return ResponseEntity.noContent().build();
    }
}

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
import java.time.format.DateTimeFormatter;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/discounts")
@CrossOrigin(origins = "*")
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

    @GetMapping("/pageAll")
    public ResponseEntity<?> getPageAll(@RequestParam Integer page, @RequestParam Integer size) {

        return ResponseEntity.ok(dotGiamGiaService.findAll(page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<?> filterDotGiamGia(
            @RequestParam(value = "tenOrMa", required = false, defaultValue = "") String tenOrMa,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThaiStr,
            @RequestParam(value = "ngayBatDau", required = false, defaultValue = "") String ngayBatDauStr,
            @RequestParam(value = "ngayKetThuc", required = false, defaultValue = "") String ngayKetThucStr,
            @RequestParam(value = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(value = "size", required = false, defaultValue = "4") String sizeStr
    ) {
        Integer page, size, trangThai;
        LocalDateTime ngayBatDau = null, ngayKetThuc = null;
        try {
            page = Integer.valueOf(pageStr);
            size = Integer.valueOf(sizeStr);
        } catch (NumberFormatException e) {
            page = 0;
            size = 4;
        }
        try {
            trangThai = trangThaiStr.isEmpty() ? null : Integer.valueOf(trangThaiStr);
        } catch (NumberFormatException e) {
            trangThai = null;
        }
        try {
            ngayBatDau = ngayBatDauStr.isEmpty() ? null : LocalDateTime.parse(ngayBatDauStr);
        } catch (Exception e) {
            System.out.println("Lỗi parse ngày bắt đầu: " + e.getMessage());
            ngayBatDau = null;
        }
        try {
            ngayKetThuc = ngayKetThucStr.isEmpty() ? null : LocalDateTime.parse(ngayKetThucStr);
        } catch (Exception e) {
            System.out.println("Lỗi parse ngày kết thúc: " + e.getMessage());
            ngayKetThuc = null;
        }
        return ResponseEntity.ok(dotGiamGiaService.filter(tenOrMa, trangThai, ngayBatDau, ngayKetThuc, page, size));
    }

    @PutMapping("/changestatus/{id}")
    public ResponseEntity<?> changeStatusDotGiamGia(@PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        dotGiamGiaService.changeStatusDotGiamGia(id);
        return ResponseEntity.ok("Bạn đã update trạng thái thành công");
    }

    @PutMapping("/changestatusStart/{id}")
    public ResponseEntity<?> changeStatusDotGiamGiaStart(@PathVariable Long id) {
        dotGiamGiaService.changeStatusDotGiamGiaStart(id);
        return ResponseEntity.ok("Bạn đã update trạng thái thành công");
    }

    @PutMapping("/changestatusStop/{id}")
    public ResponseEntity<?> changeStatusDotGiamGiaStop(@PathVariable Long id) {
        dotGiamGiaService.changeStatusDotGiamGiaStop(id);
        return ResponseEntity.ok("Bạn đã update trạng thái thành công");
    }

    @PutMapping("/changestatusdelete/{id}")
    public ResponseEntity<?> deleteDotGiamGiaStop(@PathVariable Long id) {
        dotGiamGiaService.deleteDotGiamGia(id);
        return ResponseEntity.ok("Bạn đã delete trạng thái thành công");
    }

    @GetMapping("/findbyidspctandactive")
    public ResponseEntity<?> findByIdSpctAndActive(@PathVariable Long idSpct) {
        return ResponseEntity.ok(dotGiamGiaService.getDotGiamGiaBySPCTId(idSpct));
    }
}

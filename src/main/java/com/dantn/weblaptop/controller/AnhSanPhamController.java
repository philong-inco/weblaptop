package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.service.AnhSanPhamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/anh-san-pham/")
public class AnhSanPhamController {
    AnhSanPhamService anhSanPhamService;

    @GetMapping("list")
    public ResponseEntity<ResponseLong<List<String>>> getAllAnh(){
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Get successfully", anhSanPhamService.getAll()
        ));
    }
}

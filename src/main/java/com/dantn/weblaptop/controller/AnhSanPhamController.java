package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.service.AnhSanPhamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("find-by-spct-id")
    public ResponseEntity<ResponseLong<List<String>>> findByProductDetailId(@RequestParam("idSPCT") Long idSPCT){
        List<String> result = new ArrayList<>();
        List<AnhSanPhamResponse> response = anhSanPhamService.getAllBySPCTId(idSPCT);
        if (response != null && response.size() > 0){
            result = response.stream().map(x -> x.getUrl())
                    .collect(Collectors.toSet())
                    .stream().collect(Collectors.toList());
        }
        return ResponseEntity.ok().body(new ResponseLong<>(
                200, "Get successfully", result
        ));
    }
}

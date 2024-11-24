package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.BanPhimResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.VGA;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.VGACreate;
import com.dantn.weblaptop.dto.request.update_request.VGAUpdate;
import com.dantn.weblaptop.dto.response.VGAResponse;
import com.dantn.weblaptop.service.impl.VGAService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vga/")
public class VGAController extends GenericsController<VGA, Long, VGACreate, VGAUpdate, VGAResponse> {
    private final VGAService service;
    public VGAController(@Qualifier("VGAService") GenericsService<VGA, Long, VGACreate, VGAUpdate, VGAResponse> genericsService, VGAService service) {
        super(genericsService);
        this.service = service;
    }
    @GetMapping("find/filter-id")
    public ResponseEntity<ResponseLong<List<VGAResponse>>> findByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "10") String sizeStr,
            @RequestParam(value = "name", required = false, defaultValue = "") String ten,
            @RequestParam(value = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThai
    ) {
        Sort sort = Sort.by("ngayTao").descending();
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr),sort);
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10,sort);
        }
        Page<VGAResponse> pageResult = service.findByFilter(ten, ma, trangThai, pageable);
        ResponseLong<List<VGAResponse>> result = new ResponseLong<>(
                200,
                "Find successfully",
                pageResult.getContent(),
                String.valueOf(pageResult.getNumber()),
                String.valueOf(pageResult.getSize()),
                String.valueOf(pageResult.getTotalPages()),
                String.valueOf(pageResult.getTotalElements()));

        return ResponseEntity.ok().body(result);
    }
}

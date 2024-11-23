package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.response.BanPhimResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.MauSacCreate;
import com.dantn.weblaptop.dto.request.update_request.MauSacUpdate;
import com.dantn.weblaptop.dto.response.MauSacResponse;
import com.dantn.weblaptop.service.impl.MauSacService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mau-sac/")
public class MauSacController extends GenericsController<MauSac, Long, MauSacCreate, MauSacUpdate, MauSacResponse> {
    private final MauSacService service;
    public MauSacController(@Qualifier("mauSacService")GenericsService<MauSac, Long, MauSacCreate, MauSacUpdate, MauSacResponse> genericsService, MauSacService service) {
        super(genericsService);
        this.service = service;
    }
    @GetMapping("find/filter-id")
    public ResponseEntity<ResponseLong<List<MauSacResponse>>> findByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "10") String sizeStr,
            @RequestParam(value = "name", required = false, defaultValue = "") String ten,
            @RequestParam(value = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThai
    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
        Page<MauSacResponse> pageResult = service.findByFilter(ten, ma, trangThai, pageable);
        ResponseLong<List<MauSacResponse>> result = new ResponseLong<>(
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

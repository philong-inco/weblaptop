package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.FindRamFilter;
import com.dantn.weblaptop.dto.request.create_request.RAMCreate;
import com.dantn.weblaptop.dto.request.update_request.RAMUpdate;
import com.dantn.weblaptop.dto.response.RAMResponse;
import com.dantn.weblaptop.dto.response.ResponseLong;
import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.service.impl.RAMService;
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
@RequestMapping("/api/ram/")
public class RamController extends GenericsController<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> {

    private final RAMService ramService;

    public RamController(@Qualifier("RAMService") GenericsService<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> genericsService, RAMService ramService) {
        super(genericsService);
        this.ramService = ramService;
    }

    @GetMapping("find/filter-id")
    public ResponseEntity<ResponseLong<List<RAMResponse>>> findByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") String pageStr,
            @RequestParam(name = "size", required = false, defaultValue = "10") String sizeStr,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(value = "dungLuong", required = false, defaultValue = "") String dungLuong,
            @RequestParam(value = "tocDoBus", required = false, defaultValue = "") String tocDoBus,
            @RequestParam(value = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "ngayTaoTruoc", required = false, defaultValue = "") String ngayTaoTruoc,
            @RequestParam(value = "ngaySuaTruoc", required = false, defaultValue = "") String ngaySuaTruoc,
            @RequestParam(value = "ngayTaoSau", required = false, defaultValue = "") String ngayTaoSau,
            @RequestParam(value = "ngaySuaSau", required = false, defaultValue = "") String ngaySuaSau

    ) {
        Pageable pageable;
        try {
            pageable = PageRequest.of(Integer.valueOf(pageStr), Integer.valueOf(sizeStr));
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
        FindRamFilter filter = FindRamFilter.builder()
                .name(name)
                .ma(ma)
                .dungLuong(dungLuong)
                .tocDoBus(tocDoBus)
                .trangThai(trangThai)
                .ngaySuaSau(ngaySuaSau)
                .ngaySuaTruoc(ngaySuaTruoc)
                .ngayTaoSau(ngayTaoSau)
                .ngayTaoTruoc(ngayTaoTruoc)
                .build();

        Page<RAMResponse> pageResult = ramService.findByFilter(filter, pageable);
        ResponseLong<List<RAMResponse>> result = new ResponseLong<>(
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

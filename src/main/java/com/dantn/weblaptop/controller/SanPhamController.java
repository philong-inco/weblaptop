package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.dto.request.create_request.FindSanPhamFilter;
import com.dantn.weblaptop.dto.request.create_request.SanPhamCreate;
import com.dantn.weblaptop.dto.request.update_request.SanPhamUpdate;
import com.dantn.weblaptop.dto.response.SanPhamResponse;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.service.impl.SanPhamService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham/")
public class SanPhamController extends GenericsController<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {

    private final SanPhamService sanPhamService;

    public SanPhamController(@Qualifier("sanPhamService") GenericsService<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> genericsService,
                             SanPhamService sanPhamService) {
        super(genericsService);
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("find")
    public List<SanPham> findByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") String page,
            @RequestParam(name = "size", required = false, defaultValue = "10") String size,
            @RequestParam(name = "tenSanPham", required = false, defaultValue = "") String tenSanPham,
            @RequestParam(name = "ma", required = false, defaultValue = "") String ma,
            @RequestParam(name = "ngayTaoTruoc", required = false, defaultValue = "") String ngayTaoTruoc,
            @RequestParam(name = "ngayTaoSau", required = false, defaultValue = "") String ngayTaoSau,
            @RequestParam(name = "ngaySuaTruoc", required = false, defaultValue = "") String ngaySuaTruoc,
            @RequestParam(name = "ngaySuaSau", required = false, defaultValue = "") String ngaySuaSau,
            @RequestParam(name = "trangThai", required = false, defaultValue = "") String trangThai,
            @RequestParam(value = "tenNhuCau", required = false, defaultValue = "") String[] tenNhuCau,
            @RequestParam(value = "tenThuongHieu", required = false, defaultValue = "") String[] tenThuongHieu,
            @RequestParam(value = "tenRam", required = false, defaultValue = "") String[] tenRam,
            @RequestParam(value = "tenMau", required = false, defaultValue = "") String[] tenMau,
            @RequestParam(value = "tenCPU", required = false, defaultValue = "") String[] tenCPU,
            @RequestParam(value = "tenVGA", required = false, defaultValue = "") String[] tenVGA,
            @RequestParam(value = "tenWebcam", required = false, defaultValue = "") String[] tenWebcam,
            @RequestParam(value = "tenOCung", required = false, defaultValue = "") String[] tenOCung,
            @RequestParam(value = "tenManHinh", required = false, defaultValue = "") String[] tenManHinh,
            @RequestParam(value = "tenHeDieuHanh", required = false, defaultValue = "") String[] tenHeDieuHanh,
            @RequestParam(value = "tenBanPhim", required = false, defaultValue = "") String[] tenBanPhim
    ) {
        FindSanPhamFilter filter = FindSanPhamFilter.builder()
                .tenSanPham(tenSanPham)
                .ma(ma)
                .trangThai(trangThai)
                .ngayTaoTruoc(ngayTaoTruoc)
                .ngayTaoSau(ngayTaoSau)
                .ngaySuaTruoc(ngaySuaTruoc)
                .ngaySuaSau(ngaySuaSau)
                .tenNhuCau(tenNhuCau)
                .tenThuongHieu(tenThuongHieu)
                .tenRam(tenRam)
                .tenMau(tenMau)
                .tenCPU(tenCPU)
                .tenVGA(tenVGA)
                .tenWebcam(tenWebcam)
                .tenOCung(tenOCung)
                .tenManHinh(tenManHinh)
                .tenHeDieuHanh(tenHeDieuHanh)
                .tenBanPhim(tenBanPhim)
                .build();

        return sanPhamService.findWithFilter(filter);
    }
}

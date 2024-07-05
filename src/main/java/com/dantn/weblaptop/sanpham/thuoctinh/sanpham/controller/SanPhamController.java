package com.dantn.weblaptop.sanpham.thuoctinh.sanpham.controller;

import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.sanpham.dto.request.SanPhamCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.sanpham.dto.request.SanPhamUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.sanpham.dto.response.SanPhamResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/san-pham/")
public class SanPhamController extends GenericsController<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> {
    public SanPhamController(@Qualifier("sanPhamService") GenericsService<SanPham, Long, SanPhamCreate, SanPhamUpdate, SanPhamResponse> genericsService) {
        super(genericsService);
    }
}

package com.dantn.weblaptop.sanpham.thuoctinh.thuonghieu.controller;

import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.thuonghieu.dto.request.ThuongHieuCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.thuonghieu.dto.request.ThuongHieuUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.thuonghieu.dto.response.ThuongHieuResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thuong-hieu/")
public class ThuongHieuController extends GenericsController<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> {
    public ThuongHieuController(@Qualifier("thuongHieuService") GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> genericsService) {
        super(genericsService);
    }
}

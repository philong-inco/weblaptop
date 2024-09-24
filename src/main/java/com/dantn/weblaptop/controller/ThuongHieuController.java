package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.ThuongHieuCreate;
import com.dantn.weblaptop.dto.request.update_request.ThuongHieuUpdate;
import com.dantn.weblaptop.dto.response.ThuongHieuResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/thuong-hieu/")
public class ThuongHieuController extends GenericsController<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> {
    public ThuongHieuController(@Qualifier("thuongHieuService") GenericsService<ThuongHieu, Long, ThuongHieuCreate, ThuongHieuUpdate, ThuongHieuResponse> genericsService) {
        super(genericsService);
    }
}

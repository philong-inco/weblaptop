package com.dantn.weblaptop.sanpham.thuoctinh.banphim.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.request.BanPhimCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.request.BanPhimUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.banphim.dto.response.BanPhimResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ban-phim/")
public class BanPhimController extends GenericsController<BanPhim, Long, BanPhimCreate, BanPhimUpdate, BanPhimResponse> {
    public BanPhimController(@Qualifier("banPhimService")GenericsService<BanPhim, Long, BanPhimCreate, BanPhimUpdate, BanPhimResponse> genericsService) {
        super(genericsService);
    }
}

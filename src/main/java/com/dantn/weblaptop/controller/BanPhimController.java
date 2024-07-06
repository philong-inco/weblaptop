package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.BanPhim;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.BanPhimCreate;
import com.dantn.weblaptop.dto.request.update_request.BanPhimUpdate;
import com.dantn.weblaptop.dto.response.BanPhimResponse;
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

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.HeDieuHanh;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.HeDieuHanhCreate;
import com.dantn.weblaptop.dto.request.update_request.HeDieuHanhUpdate;
import com.dantn.weblaptop.dto.response.HeDieuHanhResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/he-dieu-hanh/")
public class HeDieuHanhController extends GenericsController<HeDieuHanh, Long, HeDieuHanhCreate, HeDieuHanhUpdate, HeDieuHanhResponse> {
    public HeDieuHanhController(@Qualifier("heDieuHanhService")GenericsService<HeDieuHanh, Long, HeDieuHanhCreate, HeDieuHanhUpdate, HeDieuHanhResponse> genericsService) {
        super(genericsService);
    }
}

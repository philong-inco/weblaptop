package com.dantn.weblaptop.sanpham.thuoctinh.manhinh.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.ManHinh;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request.ManHinhUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.response.ManHinhResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/man-hinh/")
public class ManHinhController extends GenericsController<ManHinh, Long, ManHinhCreate, ManHinhUpdate, ManHinhResponse> {
    public ManHinhController(@Qualifier("manHinhService")GenericsService<ManHinh, Long, ManHinhCreate, ManHinhUpdate, ManHinhResponse> genericsService) {
        super(genericsService);
    }
}

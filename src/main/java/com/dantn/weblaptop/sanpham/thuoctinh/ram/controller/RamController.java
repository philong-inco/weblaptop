package com.dantn.weblaptop.sanpham.thuoctinh.ram.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.RAM;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request.RAMUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.response.RAMResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ram/")
public class RamController extends GenericsController<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> {
    public RamController(@Qualifier("RAMService") GenericsService<RAM, Long, RAMCreate, RAMUpdate, RAMResponse> genericsService) {
        super(genericsService);
    }

}

package com.dantn.weblaptop.sanpham.thuoctinh.cpu.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.request.CPUCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.request.CPUUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.cpu.dto.response.CPUResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cpu/")
public class CPUController extends GenericsController<CPU, Long, CPUCreate, CPUUpdate, CPUResponse> {
    public CPUController(@Qualifier("CPUService")GenericsService<CPU, Long, CPUCreate, CPUUpdate, CPUResponse> genericsService) {
        super(genericsService);
    }
}

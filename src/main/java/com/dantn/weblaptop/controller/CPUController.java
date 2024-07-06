package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.CPU;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.CPUCreate;
import com.dantn.weblaptop.dto.request.update_request.CPUUpdate;
import com.dantn.weblaptop.dto.response.CPUResponse;
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

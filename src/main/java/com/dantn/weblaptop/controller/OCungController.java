package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.OCung;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.OCungCreate;
import com.dantn.weblaptop.dto.request.update_request.OCungUpdate;
import com.dantn.weblaptop.dto.response.OCungResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/o-cung/")
public class OCungController extends GenericsController<OCung, Long, OCungCreate, OCungUpdate, OCungResponse> {
    public OCungController(@Qualifier("OCungService") GenericsService<OCung, Long, OCungCreate, OCungUpdate, OCungResponse> genericsService) {
        super(genericsService);
    }
}

package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.NhuCauCreate;
import com.dantn.weblaptop.dto.request.update_request.NhuCauUpdate;
import com.dantn.weblaptop.dto.response.NhuCauResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nhu-cau/")
public class NhuCauController extends GenericsController<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse>  {
    public NhuCauController(@Qualifier("nhuCauService") GenericsService<NhuCau, Long, NhuCauCreate, NhuCauUpdate, NhuCauResponse> genericsService) {
        super(genericsService);
    }
}

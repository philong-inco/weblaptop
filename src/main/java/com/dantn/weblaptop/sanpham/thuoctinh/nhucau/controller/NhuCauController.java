package com.dantn.weblaptop.sanpham.thuoctinh.nhucau.controller;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.request.NhuCauUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.nhucau.dto.response.NhuCauResponse;
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

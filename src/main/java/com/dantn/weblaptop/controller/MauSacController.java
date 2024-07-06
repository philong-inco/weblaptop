package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.MauSac;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.MauSacCreate;
import com.dantn.weblaptop.dto.request.update_request.MauSacUpdate;
import com.dantn.weblaptop.dto.response.MauSacResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mau-sac/")
public class MauSacController extends GenericsController<MauSac, Long, MauSacCreate, MauSacUpdate, MauSacResponse> {
    public MauSacController(@Qualifier("mauSacService")GenericsService<MauSac, Long, MauSacCreate, MauSacUpdate, MauSacResponse> genericsService) {
        super(genericsService);
    }
}

package com.dantn.weblaptop.sanpham.thuoctinh.webcam.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.sanpham.generics.GenericsController;
import com.dantn.weblaptop.sanpham.generics.GenericsService;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.request.WebcamCreate;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.request.WebcamUpdate;
import com.dantn.weblaptop.sanpham.thuoctinh.webcam.dto.response.WebcamResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webcam/")
public class WebcamController extends GenericsController<Webcam, Long, WebcamCreate, WebcamUpdate, WebcamResponse> {
    public WebcamController(@Qualifier("webcamService") GenericsService<Webcam, Long, WebcamCreate, WebcamUpdate, WebcamResponse> genericsService) {
        super(genericsService);
    }
}

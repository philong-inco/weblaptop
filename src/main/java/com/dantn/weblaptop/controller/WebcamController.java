package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.entity.sanpham.thuoctinh.Webcam;
import com.dantn.weblaptop.generics.GenericsController;
import com.dantn.weblaptop.generics.GenericsService;
import com.dantn.weblaptop.dto.request.create_request.WebcamCreate;
import com.dantn.weblaptop.dto.request.update_request.WebcamUpdate;
import com.dantn.weblaptop.dto.response.WebcamResponse;
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

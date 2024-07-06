package com.dantn.weblaptop.hoadon.controller;

import com.dantn.weblaptop.hoadon.service.GiaoHangThanhService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiaoHangNhanhController {

    GiaoHangThanhService giaoHangThanhService;

    @PostMapping()
    public String createOrder(@RequestBody Map<String, Object> orderData) throws IOException, InterruptedException {
            return giaoHangThanhService.createOrder(orderData);
    }
}

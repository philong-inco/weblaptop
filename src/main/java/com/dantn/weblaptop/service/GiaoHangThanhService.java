package com.dantn.weblaptop.hoadon.service;

import java.io.IOException;
import java.util.Map;

public interface GiaoHangThanhService {
    String createOrder(Map<String, Object> orderData) throws IOException, InterruptedException;
}

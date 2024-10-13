package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.repository.GioHangChiTIetRepository;
import com.dantn.weblaptop.repository.GioHangRepository;
import com.dantn.weblaptop.service.GioHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GioHangServiceImpl implements GioHangService {
    GioHangRepository gioHangRepository;
    @Override
    public void createGioHang(KhachHang khachHang) {
        GioHang cart = new GioHang();
        cart.setKhachHang(khachHang);
        gioHangRepository.save(cart);
        System.out.println("Giỏ hàng oke");
    }
}

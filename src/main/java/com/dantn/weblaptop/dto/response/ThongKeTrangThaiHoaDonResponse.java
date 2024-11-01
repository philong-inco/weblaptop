package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.constant.HoaDonStatus;
import org.springframework.beans.factory.annotation.Value;

public interface ThongKeTrangThaiHoaDonResponse {
    @Value("#{target.trangThai}")
    HoaDonStatus getTrangThai();
    @Value("#{target.soLuong}")
    Integer getSoLuong();
}

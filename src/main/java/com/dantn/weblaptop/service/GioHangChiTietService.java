package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.update_request.UpdateSoLongRequest;
import com.dantn.weblaptop.exception.AppException;

public interface GioHangChiTietService {
    Boolean deleteCartDetail(Long id);
    String changeQuantity(UpdateSoLongRequest changeQuantity) throws AppException;
    void deleteAllCart(Long idKhachHang);
}

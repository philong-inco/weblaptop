package com.dantn.weblaptop.service;

import com.dantn.weblaptop.dto.request.create_request.GioHangRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateSoLongRequest;
import com.dantn.weblaptop.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;

public interface GioHangChiTietService {
    Boolean deleteCartDetail(Long id);
    String changeQuantity(UpdateSoLongRequest changeQuantity) throws AppException;
    String deleteAllCart(String sessionId , Long idKhachHang);
}

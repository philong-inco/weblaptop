package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.GioHangRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateSoLongRequest;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.repository.GioHangChiTietRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.GioHangChiTietService;
import com.dantn.weblaptop.service.SerialNumberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    GioHangChiTietRepository gioHangChiTietRepository;
    SerialNumberService serialNumberService;


    @Override
    public Boolean deleteCartDetail(Long id) {
        gioHangChiTietRepository.deleteById(id);
        return true;
    }

    @Override
    public String changeQuantity(UpdateSoLongRequest changeQuantity) throws AppException {
        GioHangChiTiet cartDetail = gioHangChiTietRepository.findById(changeQuantity.getIdGioHangChiTiet()).get();
        Integer quantity = Optional.ofNullable(
                        serialNumberService.getSerialNumberByProductIdAndStatus(cartDetail.getSanPhamChiTiet().getId(), 0))
                .map(List::size)
                .orElse(0);
        if (quantity < changeQuantity.getSoLuong()) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_NOT_ENOUGH);
        }
        cartDetail.setSoLuong(changeQuantity.getSoLuong());
        gioHangChiTietRepository.save(cartDetail);
        return "ok";
    }

    @Override
    public String deleteAllCart(GioHangRequest cartRequest) {
        if (cartRequest.getIdKhachHang()!=null) {
            gioHangChiTietRepository.deleteAllCart(cartRequest.getIdKhachHang());
            return "ok xóa giỏ hàng của khách hàng";
        } else if(cartRequest.getSessionId()!=null && !cartRequest.getSessionId().isEmpty()) {
            gioHangChiTietRepository.deleteAllCartBySessionId(cartRequest.getSessionId());
            return "Xóa giỏ hàng session id";
        }else {
            return "Không tìm thấy giỏ hàng";
        }
    }
}

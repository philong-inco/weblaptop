package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.AddToGioHangRequest;
import com.dantn.weblaptop.dto.response.GioHangDetailResponse;
import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.GioHangChiTietMapper;
import com.dantn.weblaptop.repository.GioHangChiTietRepository;
import com.dantn.weblaptop.repository.GioHangRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.GioHangService;
import com.dantn.weblaptop.service.SerialNumberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GioHangServiceImpl implements GioHangService {
    GioHangChiTietRepository gioHangChiTIetRepository;
    GioHangRepository gioHangRepository;
    GioHangChiTietRepository gioHangChiTietRepository;
    KhachHangRepository khachHangRepository;
    SanPhamChiTietRepository sanPhamChiTietRepository;
    GioHangChiTietMapper gioHangChiTietMapper;
    SerialNumberService serialNumberService;

    @Override
    public void createGioHang(KhachHang khachHang) {
        GioHang cart = new GioHang();
        cart.setKhachHang(khachHang);
        gioHangRepository.save(cart);
        System.out.println("Giỏ hàng oke");
    }

    @Override
    public GioHang addToCart(AddToGioHangRequest request, HttpServletRequest httpServletRequest) throws AppException {
        Integer quantity = Optional.ofNullable(serialNumberService.getSerialNumberByProductIdAndStatus(request.getIdSanPham(), 0))
                .map(List::size)
                .orElse(0);
        if (quantity < request.getSoLuong()) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_NOT_ENOUGH);
        }

        Optional<GioHang> cartCustomerOption = Optional.empty();
        if(request.getIdKhachHang()!=null){
            cartCustomerOption=   gioHangRepository.findByKhachHangId(request.getIdKhachHang());
        }
        Optional<GioHang> cartSessionIdOption = gioHangRepository.findBySessionId(httpServletRequest.getSession().getId());
        if (!cartCustomerOption.isPresent() && !cartSessionIdOption.isPresent() ) {
            GioHang cart = new GioHang();
            if(request.getIdKhachHang()!=null){
                cart.setKhachHang(khachHangRepository.findById(request.getIdKhachHang()).get());
            }else{
                cart.setKhachHang(null);
                cart.setSessionId(httpServletRequest.getSession().getId());
            }
            gioHangRepository.save(cart);
            GioHangChiTiet cartDetail = new GioHangChiTiet();
            cartDetail.setGioHang(cart);
            cartDetail.setSanPhamChiTiet(sanPhamChiTietRepository.findById(request.getIdSanPham()).get());
            cartDetail.setSoLuong(request.getSoLuong());
            cartDetail.setGia(request.getGia());
            cartDetail.setTrangThai(0);// 0 đang sử dụng
            gioHangChiTIetRepository.save(cartDetail);
        } else {
            GioHang cart = new GioHang();
            if (request.getIdKhachHang() != null) {
                cart = cartCustomerOption.get();
            } else {
                cart = cartSessionIdOption.get();
            }

            List<GioHangChiTiet> listCartDetailOld = gioHangChiTietRepository.getGioHangChiTietByGioHangId(cart.getId());
            if (!listCartDetailOld.isEmpty()) {
                boolean foundInOld = false;
                for (GioHangChiTiet cartDetailOld : listCartDetailOld) {
                    System.out.println("ID sản phẩm : " + cartDetailOld.getSanPhamChiTiet().getId());
                    if (Objects.equals(cartDetailOld.getSanPhamChiTiet().getId(), request.getIdSanPham())) {
                        GioHangChiTiet cartDetail = gioHangChiTietRepository.findById(cartDetailOld.getId()).get();
                        if (quantity <cartDetail.getSoLuong() + request.getSoLuong()) {
                            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_NOT_ENOUGH);
                        }
                        cartDetail.setSoLuong(cartDetail.getSoLuong() + request.getSoLuong());
                        gioHangChiTietRepository.save(cartDetail);
                        foundInOld = true;
                        break;
                    }
                }
                if (!foundInOld) {
                    GioHangChiTiet cartDetail = GioHangChiTiet
                            .builder()
                            .sanPhamChiTiet(sanPhamChiTietRepository.findById(request.getIdSanPham()).get())
                            .gioHang(cart)
                            .gia(request.getGia())
                            .soLuong(request.getSoLuong())
                            .trangThai(0)
                            .build();
                    gioHangChiTietRepository.save(cartDetail);
                }
            } else {

                GioHangChiTiet cartDetail = GioHangChiTiet.builder().sanPhamChiTiet(sanPhamChiTietRepository.findById(
                                request.getIdSanPham()).get())
                        .gioHang(cart)
                        .gia(request.getGia())
                        .soLuong(request.getSoLuong())
                        .trangThai(0)
                        .build();
                gioHangChiTIetRepository.save(cartDetail);
            }
        }
        return null;
    }

    @Override
    public List<GioHangDetailResponse> getListCart(Long idKhachHang , HttpServletRequest httpServletRequest) throws AppException {
        GioHang gioHang = new GioHang();
        if(idKhachHang!=null){
            Optional<KhachHang> optional = khachHangRepository.findById(idKhachHang);
            if (optional.isEmpty()) {
                throw new AppException(ErrorCode.CUSTOMER_NOT_FOUNT);
            }
            gioHang = gioHangRepository.findByKhachHangId(idKhachHang).get();
        }else{
            Optional<GioHang> cartSessionIdOption = gioHangRepository.findBySessionId(httpServletRequest.getSession().getId());
            if(cartSessionIdOption.isEmpty()){
                gioHang.setSessionId(httpServletRequest.getSession().getId());
                gioHang = gioHangRepository.save(gioHang);
            }else{
                gioHang = cartSessionIdOption.get();
            }
        }
        List<GioHangChiTiet> listCartDetail = gioHangChiTIetRepository.getGioHangChiTietByGioHangId(gioHang.getId());
        return listCartDetail.stream().map(
                gioHangChiTietMapper::toResponse
        ).toList();
    }

    @Override
    public Integer quantityInCart(Long idKhachHang ,  HttpServletRequest httpServletRequest) {
        if(idKhachHang!=null){
            return gioHangRepository.quantityInCart(idKhachHang);
        }
        return gioHangRepository.quantityInCartBySessionId(httpServletRequest.getSession().getId());
    }
}

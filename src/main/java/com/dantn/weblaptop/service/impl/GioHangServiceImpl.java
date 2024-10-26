package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.dto.request.create_request.AddToGioHangRequest;
import com.dantn.weblaptop.dto.request.create_request.FindSanPhamChiTietByFilter;
import com.dantn.weblaptop.dto.request.create_request.GioHangRequest;
import com.dantn.weblaptop.dto.response.CartResponse;
import com.dantn.weblaptop.dto.response.GioHangDetailResponse;
import com.dantn.weblaptop.dto.response.SanPhamChiTietClientDTO;
import com.dantn.weblaptop.entity.giohang.GioHang;
import com.dantn.weblaptop.entity.giohang.GioHangChiTiet;
import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.GioHangChiTietMapper;
import com.dantn.weblaptop.repository.GioHangChiTietRepository;
import com.dantn.weblaptop.repository.GioHangRepository;
import com.dantn.weblaptop.repository.KhachHangRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.service.GioHangService;
import com.dantn.weblaptop.service.SanPhamChiTietService;
import com.dantn.weblaptop.service.SerialNumberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    SanPhamChiTietService sanPhamChiTietService;

    @Override
    public void createGioHang(KhachHang khachHang) {
        GioHang cart = new GioHang();
        cart.setKhachHang(khachHang);
        gioHangRepository.save(cart);
        System.out.println("Giỏ hàng oke");
    }

    @Override
    public CartResponse addToCart(AddToGioHangRequest request, HttpServletRequest httpServletRequest) throws AppException {
        Integer quantity = Optional.ofNullable(serialNumberService.getSerialNumberByProductIdAndStatus(request.getIdSPCT(), 0)).map(List::size).orElse(0);
        if (quantity < request.getSoLuong()) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_NOT_ENOUGH);
        }
        GioHang cart = new GioHang();
        Optional<GioHang> cartCustomerOption = Optional.empty();
        Optional<GioHang> cartSessionIdOption = Optional.empty();
        FindSanPhamChiTietByFilter filter = new FindSanPhamChiTietByFilter();
        filter.setIdSPCT(request.getIdSPCT().toString());
        List<SanPhamChiTietClientDTO> responseList = sanPhamChiTietService.findByFilter(filter);
        if (responseList == null || responseList.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
        }
        SanPhamChiTietClientDTO response = responseList.get(0);
        BigDecimal giaBan = BigDecimal.ZERO;
        if (response.getGiaSauKhuyenMai() != null) {
            giaBan = new BigDecimal(response.getGiaSauKhuyenMai());

        } else if (response.getGiaBan() != null) {
            giaBan = new BigDecimal(response.getGiaBan());
        }
        if (request.getIdKhachHang() != null) {
            cartCustomerOption = gioHangRepository.findByKhachHangId(request.getIdKhachHang());
        } else if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
            cartSessionIdOption = gioHangRepository.findBySessionId(request.getSessionId());
        } else {
            cartSessionIdOption = gioHangRepository.findBySessionId(httpServletRequest.getSession().getId());
        }

        if (!cartCustomerOption.isPresent() && !cartSessionIdOption.isPresent()) {

            if (request.getIdKhachHang() != null) {
                cart.setKhachHang(khachHangRepository.findById(request.getIdKhachHang()).get());
            } else if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
                cart.setSessionId(request.getSessionId());
            } else {
                cart.setSessionId(httpServletRequest.getSession().getId());
            }
            gioHangRepository.save(cart);
            GioHangChiTiet cartDetail = new GioHangChiTiet();
            cartDetail.setGioHang(cart);
            cartDetail.setSanPhamChiTiet(sanPhamChiTietRepository.findById(request.getIdSPCT()).get());
            cartDetail.setSoLuong(request.getSoLuong());
            cartDetail.setGia(giaBan);
            cartDetail.setTrangThai(0);// 0 đang sử dụng
            gioHangChiTIetRepository.save(cartDetail);
        } else {
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
                    if (Objects.equals(cartDetailOld.getSanPhamChiTiet().getId(), request.getIdSPCT())) {
                        GioHangChiTiet cartDetail = gioHangChiTietRepository.findById(cartDetailOld.getId()).get();
                        if (quantity < cartDetail.getSoLuong() + request.getSoLuong()) {
                            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_NOT_ENOUGH);
                        }
                        cartDetail.setSoLuong(cartDetail.getSoLuong() + request.getSoLuong());
                        gioHangChiTietRepository.save(cartDetail);
                        foundInOld = true;
                        break;
                    }
                }
                if (!foundInOld) {
                    GioHangChiTiet cartDetail = GioHangChiTiet.builder().sanPhamChiTiet(sanPhamChiTietRepository.findById(request.getIdSPCT()).get()).gioHang(cart).gia(giaBan).soLuong(request.getSoLuong()).trangThai(0).build();
                    gioHangChiTietRepository.save(cartDetail);
                }
            } else {

                GioHangChiTiet cartDetail = GioHangChiTiet.builder().sanPhamChiTiet(sanPhamChiTietRepository.findById(request.getIdSPCT()).get()).gioHang(cart).gia(giaBan).soLuong(request.getSoLuong()).trangThai(0).build();
                gioHangChiTIetRepository.save(cartDetail);
            }
        }
        return CartResponse.builder().sessionId(cart.getSessionId()).idKhachHang(cart.getKhachHang() != null ? cart.getKhachHang().getId() : null)
//                .idSanPham(request.getIdSanPham())
//                .gia(request.getGia())
                .build();
    }

    @Override
    public List<GioHangDetailResponse> getListCart(String sessionId, Long idKhachHang) throws AppException {
        GioHang gioHang = new GioHang();
        if (idKhachHang != null) {
            Optional<KhachHang> optional = khachHangRepository.findById(idKhachHang);
            if (optional.isEmpty()) {
//                throw new AppException(ErrorCode.CUSTOMER_NOT_FOUNT);
                return new ArrayList<GioHangDetailResponse>();

            }
            Optional<GioHang> optionalCart = gioHangRepository.findByKhachHangId(idKhachHang);
            if (optionalCart.isPresent()) {
                gioHang = optionalCart.get();
            }
        } else if (sessionId != null && !sessionId.isEmpty()) {
            Optional<GioHang> cartSessionIdOption = gioHangRepository.findBySessionId(sessionId);
            if (cartSessionIdOption.isPresent()) {
                gioHang = cartSessionIdOption.get();
            } else {
                System.out.println("Lỗi giỏ hàng SessionId");
//                throw new RuntimeException("SessionId hoặc Id khách hàng : chưa có hoặc sai");
                return new ArrayList<GioHangDetailResponse>();

            }
        } else {
            return new ArrayList<GioHangDetailResponse>();
        }
        Boolean checkAll = false;
        List<GioHangChiTiet> listCartDetail = gioHangChiTIetRepository.getGioHangChiTietByGioHangId(gioHang.getId());
        for (GioHangChiTiet cartDetail : listCartDetail) {
            FindSanPhamChiTietByFilter filter = new FindSanPhamChiTietByFilter();
            filter.setIdSPCT(cartDetail.getSanPhamChiTiet().getId().toString());
            List<SanPhamChiTietClientDTO> responseList = sanPhamChiTietService.findByFilter(filter);
            if (responseList == null || responseList.isEmpty()) {
                continue;
            }
            SanPhamChiTietClientDTO response = responseList.get(0);
            Boolean check = false;
            if (response.getTonKhoConLai() != null && !response.getTonKhoConLai().isEmpty()) {
                if (Integer.parseInt(response.getTonKhoConLai()) == 0 && cartDetail.getTrangThai() == 0) {
                    cartDetail.setTrangThai(1);
                    check = true;
                }
                if ((Integer.parseInt(response.getTonKhoConLai()) < cartDetail.getSoLuong()) && (Integer.parseInt(response.getTonKhoConLai()) != 0)) {
                    cartDetail.setSoLuong(Integer.parseInt(response.getTonKhoConLai()));
                    check = true;
                }
            }
            if (response.getGiaSauKhuyenMai() != null) {
                BigDecimal giaSauKhuyenMai = new BigDecimal(response.getGiaSauKhuyenMai());
                if (cartDetail.getGia().compareTo(giaSauKhuyenMai) != 0) {
                    cartDetail.setGia(giaSauKhuyenMai);
                    check = true;
                }
            } else if (response.getGiaBan() != null) {
                BigDecimal giaBan = new BigDecimal(response.getGiaBan());
                if (cartDetail.getGia().compareTo(giaBan) != 0) {
                    cartDetail.setGia(giaBan);
                    check = true;
                }
            }
            if (check) {
                checkAll = true;
                gioHangChiTietRepository.save(cartDetail);
            }
        }
        if (checkAll) {
            List<GioHangChiTiet> listCartDetailDone = gioHangChiTIetRepository.getGioHangChiTietByGioHangId(gioHang.getId());
            return listCartDetailDone.stream().map(gioHangChiTietMapper::toResponse).toList();
        } else {
            return listCartDetail.stream().map(gioHangChiTietMapper::toResponse).toList();
        }
    }

    @Override
    public Integer quantityInCart(String sessionId, Long idKhachHang) {
        if (idKhachHang != null) {
            return gioHangRepository.quantityInCart(idKhachHang);
        } else if (sessionId != null && !sessionId.isEmpty()) {
            return gioHangRepository.quantityInCartBySessionId(sessionId);
        } else {
            return 0;
        }

    }
}

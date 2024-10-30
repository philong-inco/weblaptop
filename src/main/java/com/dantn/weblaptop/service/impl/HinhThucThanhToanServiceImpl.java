package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.config.VNPAYConfig;
import com.dantn.weblaptop.dto.request.create_request.CreateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.request.create_request.GioHangChiTietRequest;
import com.dantn.weblaptop.dto.request.update_request.UpdateHinhThucThanhToanRequest;
import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.Meta;
import com.dantn.weblaptop.dto.response.ResultPaginationResponse;
import com.dantn.weblaptop.entity.hoadon.HinhThucThanhToan;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.exception.ErrorCode;
import com.dantn.weblaptop.mapper.impl.HinhThucThanhToanMapper;
import com.dantn.weblaptop.repository.HinhThucThanhToanRepository;
import com.dantn.weblaptop.repository.SanPhamChiTietRepository;
import com.dantn.weblaptop.repository.SerialNumberRepository;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
import com.dantn.weblaptop.util.GenerateCode;
import com.dantn.weblaptop.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class HinhThucThanhToanServiceImpl implements HinhThucThanhToanService {
    @Value("${payment.vnPay.returnUrlOnline}")
    String returnUrlOnline;
    @Value("${payment.vnPay.returnUrl}")
    String returnUrl;
    String urlSuccessClient = "http://localhost:3000/";
    final String urlSuccessAdmin = "";
    final HinhThucThanhToanRepository paymentMethodRepository;
    final VNPAYConfig vnPayConfig;
    final SanPhamChiTietRepository sanPhamChiTietRepository;
    final SerialNumberRepository serialNumberRepository;

    @Override
    public ResultPaginationResponse getPaymentMethodsPage(Optional<String> page, Optional<String> size) {
        String sPage = page.isPresent() ? page.get() : "0";
        String sSize = size.isPresent() ? size.get() : "5";
        Pageable pageable = PageRequest.of(Integer.parseInt(sPage), Integer.parseInt(sSize));
        Page<HinhThucThanhToan> paymentMethodsPage = paymentMethodRepository.findAll(pageable);
        Page<HinhThucThanhToanResponse> responses = paymentMethodsPage.map(
                paymentMethod -> HinhThucThanhToanMapper.toHinhThucThanhToanResponse(paymentMethod));
        Meta meta = Meta.builder()
                .page(responses.getNumber())
                .pageSize(responses.getSize())
                .pages(responses.getTotalPages())
                .total(responses.getTotalElements())
                .build();

        ResultPaginationResponse response = ResultPaginationResponse
                .builder()
                .meta(meta)
                .result(responses.getContent())
                .build();

        return response;
    }

    @Override
    public HinhThucThanhToanResponse create(CreateHinhThucThanhToanRequest request) {
        HinhThucThanhToan paymentMethod = paymentMethodRepository.save(
                HinhThucThanhToanMapper.create(request));
        HinhThucThanhToanResponse response = HinhThucThanhToanMapper.toHinhThucThanhToanResponse(paymentMethod);
        return response;
    }

    @Override
    public HinhThucThanhToanResponse update(Long id, UpdateHinhThucThanhToanRequest request) throws AppException {
        HinhThucThanhToan existingPaymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));
        HinhThucThanhToanMapper.update(request, existingPaymentMethod);
        HinhThucThanhToanResponse response = HinhThucThanhToanMapper.toHinhThucThanhToanResponse(
                paymentMethodRepository.save(existingPaymentMethod));
        return response;
    }


    @Override
    public void updateStart(Long id) {

    }

    @Override
    public String payWithVNPAYOnline(List<GioHangChiTietRequest> cartDetail, HttpServletRequest request) throws AppException {

        for (GioHangChiTietRequest cartDetailRequest : cartDetail) {
            log.info("id SPCT : " + cartDetailRequest.getIdSPCT());
            log.info("id Gia : " + cartDetailRequest.getGia());
            log.info("id CartDetail : " + cartDetailRequest.getIdGioHangChiTiet());
            log.info("id So Luong : " + cartDetailRequest.getSoLuong());
            log.info("--------------------------");
            Optional<SanPhamChiTiet> optional = sanPhamChiTietRepository.findById(cartDetailRequest.getIdSPCT());
            if (!optional.isPresent()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
            }
            List<SerialNumber> listSerialNumber = serialNumberRepository
                    .findBySanPhamChiTietIdAndTrangThaiWithLimit
                            (cartDetailRequest.getIdSPCT(), cartDetailRequest.getSoLuong());

            if (listSerialNumber.size() < cartDetailRequest.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + optional.get().getMa() + " không đủ . Sản phẩm tồn kho : " + listSerialNumber.size());
            }
            // up lại tt sp
            Integer quantityProductIsActive = serialNumberRepository.getQuantitySerialIsActive(cartDetailRequest.getIdSPCT());
            if (quantityProductIsActive != null && quantityProductIsActive == 0) {
                optional.get().setTrangThai(0);
                sanPhamChiTietRepository.save(optional.get());
            }
        }
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(request.getParameter("amount"))).multiply(BigDecimal.valueOf(100L));
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_ReturnUrl", this.returnUrlOnline);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }

    @Override
    public void handlePaymentCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String transactionId = request.getParameter("vnp_TxnRef");
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String bankCode = request.getParameter("vnp_BankCode");
        String payDate = request.getParameter("vnp_PayDate");
        String cardType = request.getParameter("vnp_CardType");

        String successUrl = this.urlSuccessClient
                + "?transactionId=" + transactionId
                + "&amount=" + amount
                + "&status=" + status
                + "&orderInfo=" + orderInfo
                + "&bankCode=" + bankCode
                + "&payDate=" + payDate
                + "&cardType=" + cardType;

        String failureUrl = "http://frontend-url.com/failure-page"
                + "?transactionId=" + transactionId
                + "&amount=" + amount
                + "&status=" + status
                + "&orderInfo=" + orderInfo
                + "&bankCode=" + bankCode
                + "&payDate=" + payDate
                + "&cardType=" + cardType;

        if ("00".equals(status)) {
            response.sendRedirect(successUrl);
        } else {
            response.sendRedirect(failureUrl);
        }
    }
}


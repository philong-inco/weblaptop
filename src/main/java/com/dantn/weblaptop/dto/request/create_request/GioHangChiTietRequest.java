package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHangChiTietRequest {
    @NotNull(message = "PRODUCT_DETAIL_IS_NULL")
    Long idSPCT;
    @NotNull(message = "PRICE_NOT_NULL")
    BigDecimal gia;
    @Min(value = 1, message = "MINIMUM_NUMBER_OF_PRODUCTS_IS_1")
    Integer soLuong;
}

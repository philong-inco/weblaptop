package com.dantn.weblaptop.dto.response.pdf;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberDaBanPdfResponse {
    Long billId;
    Long productDetailId;
    String productDetailCode;
    String productName;
    String price;
    Integer quantity;
    String totalPrice;
}

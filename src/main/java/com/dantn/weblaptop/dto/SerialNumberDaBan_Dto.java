package com.dantn.weblaptop.dto;

import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberDaBan_Dto {
    String productName;
    Long productId;
    Long totalSerialSold;
}

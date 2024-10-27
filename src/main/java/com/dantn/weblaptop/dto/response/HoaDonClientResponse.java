package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonClientResponse {
    HoaDonResponse hoaDon;
    List<SerialNumberDaBanResponse> serialNumber;
    List<LichSuHoaDonResponse> lichSuHoaDon;

}

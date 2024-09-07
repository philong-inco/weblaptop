package com.dantn.weblaptop.dto.request.create_request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSerialNumberDaBanRequest {
// bỏ
    Long idHoaDon;
    Long idSanPham;
    Long idSerialNumber;
}

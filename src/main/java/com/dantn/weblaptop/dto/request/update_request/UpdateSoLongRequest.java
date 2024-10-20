package com.dantn.weblaptop.dto.request.update_request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSoLongRequest {

    Long idGioHangChiTiet;
    @Min(value = 1, message = "MINIMUM_NUMBER_OF_PRODUCTS_IS_1")
    Integer soLuong;
}

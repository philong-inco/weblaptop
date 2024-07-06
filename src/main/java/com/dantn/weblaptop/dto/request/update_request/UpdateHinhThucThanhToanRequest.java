package com.dantn.weblaptop.dto.request.update_request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateHinhThucThanhToanRequest {
    Long id;
    String ma;
    @NotBlank(message = "Tên phương thức thanh toán không được để trống")
    String ten;
    String moTa;
}

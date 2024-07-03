package com.dantn.weblaptop.hoadon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HinhThucThanhToanRequest {
    Long id;
    String ma;
    @JsonProperty("trang_thai")
    Integer trangThai;
    @NotBlank(message = "Tên phương thức thanh toán không được để trống")
    String ten;
    @JsonProperty("mo_ta")
    String moTa;
}

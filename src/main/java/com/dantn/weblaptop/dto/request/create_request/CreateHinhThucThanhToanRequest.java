package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateHinhThucThanhToanRequest {
    //    NguyenTienManh
    @NotBlank(message = "Tên hình thức không được để trống")
    @Size(min = 3, max = 50, message = "Tên tử 5 đến 50 ký tự")
    String ten;
    String moTa;
}

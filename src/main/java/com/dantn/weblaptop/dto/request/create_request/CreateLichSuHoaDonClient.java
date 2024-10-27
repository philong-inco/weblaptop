package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLichSuHoaDonClient {
    @NotBlank(message = "NOTE_NOT_BLANK")
    @Size(min = 10, message = "NOTE_MIN_20")
    private String ghiChuKhachHang;
}

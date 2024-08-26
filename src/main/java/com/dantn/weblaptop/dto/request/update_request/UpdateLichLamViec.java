package com.dantn.weblaptop.dto.request.update_request;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLichLamViec {
    @JsonProperty("chuThich")
    @NotEmpty(message = "Chú thích không được để trống")
    @Size(max = 255, message = "Chú thích không được vượt quá 255 ký tự")
    private String chuThich;
    private Long idNhanVien;
    private Long idCaLamViec;
}

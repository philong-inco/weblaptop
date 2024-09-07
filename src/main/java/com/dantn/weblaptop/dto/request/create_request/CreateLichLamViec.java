package com.dantn.weblaptop.dto.request.create_request;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateLichLamViec {
    @JsonProperty("chuThich")
    @Size(max = 255, message = "Chú thích không được vượt quá 255 ký tự")
    private String chuThich;

    private Long idNhanVien;

    private Long idCaLamViec;

    private LocalDate ngayLamViec;

}


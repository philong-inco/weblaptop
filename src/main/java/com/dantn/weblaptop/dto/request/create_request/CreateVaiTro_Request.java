package com.dantn.weblaptop.dto.request.create_request;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateVaiTro_Request {
    Long id;
    @JsonProperty("ma")
    String ma;
    @JsonProperty("trang_thai")
    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Trạng thái phải lớn hơn hoặc bằng 0")
    @Max(value = 1, message = "Trạng thái phải nhỏ hơn hoặc bằng 1")
    Integer trangThai;
    @JsonProperty("ten")
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 1, max = 255, message = "Tên không được vượt quá 255 ký tự")
    String ten;
    @JsonProperty("mo_ta")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(min = 1, max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    String moTa;
}

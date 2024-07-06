package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RAMCreate{
    @NotBlank(message = "Ten khong bo trong")
    String ten;
    String dungLuong;
    String tocDoBus;
    Integer trangThai;
}

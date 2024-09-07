package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CaLamViec_Response {
    Long id;
    Integer ca;
    String moTa;
    Integer trangThai;
    LocalDate ngayLamViec;
}

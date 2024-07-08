package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhachHangPhieuGiamGiaResponse {

    Long id;
    Long idKhachHang;
    String maPhieu;
    Integer trangThai;
    String email;

}

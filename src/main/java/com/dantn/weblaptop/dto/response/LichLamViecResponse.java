package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichLamViecResponse {
    private String chuThich;
    private NhanVien nhanVien;
    private CaLamViec caLamViec;
}

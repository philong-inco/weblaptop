package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.entity.nhanvien.CaLamViec;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichLamViecResponse {
    private String chuThich;
    private Long nhanVien;
    private Long caLamViec;
    private LocalDate ngayLamViec;
}

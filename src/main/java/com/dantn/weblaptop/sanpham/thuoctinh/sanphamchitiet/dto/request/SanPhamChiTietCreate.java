package com.dantn.weblaptop.sanpham.thuoctinh.sanphamchitiet.dto.request;

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
public class SanPhamChiTietCreate {
    String giaBan;
    Integer trangThai;
    Long banPhimId;
    Long cpuId;
    Long heDieuHanhId;
    Long manHinhId;
    Long mauSacId;
    Long oCungId;
    Long ramId;
    Long sanPhamId;
    Long vgaId;
    Long webcamId;
}

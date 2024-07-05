package com.dantn.weblaptop.sanpham.thuoctinh.manhinh.dto.request;

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
public class ManHinhUpdate {
    Long id;
    String ten;
    String doPhanGiai;
    String kichThuoc;
    Integer trangThai;
}

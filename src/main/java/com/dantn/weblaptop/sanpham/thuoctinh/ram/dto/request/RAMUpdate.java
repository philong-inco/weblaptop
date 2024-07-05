package com.dantn.weblaptop.sanpham.thuoctinh.ram.dto.request;

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
public class RAMUpdate {
    Long id;
    String ten;
    String dungLuong;
    String tocDoBus;
    Integer trangThai;
}

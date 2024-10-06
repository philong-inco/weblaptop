package com.dantn.weblaptop.dto.response;

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
public class SanPhamClientDTO {

    Long id;
    String ten;
    String moTa;
    String nhuCau;
    String thuongHieu;
    String giaMin;
    String giaMax;
    String giaSauKhuyenMai;
    String soTienGiam;
    String tenKhuyenMai;
    Boolean banChay;
    String image;



}


package com.dantn.weblaptop.dto.request.create_request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FilterDotGiamGia {
    private String ma;
    private String ten;
    private String trangThai;
    private String thoiGianBatDauTruoc;
    private String thoiGianBatDauSau;
    private String thoiGianKetThucTruoc;
    private String thoiGianKetThucSau;
    private String giaTri;
    private String giaTriNhoHon;
    private String giaTriLonHon;
}

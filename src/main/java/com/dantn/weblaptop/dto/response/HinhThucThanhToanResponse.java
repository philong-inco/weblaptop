package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HinhThucThanhToanResponse extends BaseResponse{
    String ten;
    String ma;
    String moTa;
    String trangThai;
}

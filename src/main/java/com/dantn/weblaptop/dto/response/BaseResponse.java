package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {
    Long id;

    String ngayTao;

    Long ngaySua;

    String nguoiTao;

    String nguoiSua;
}

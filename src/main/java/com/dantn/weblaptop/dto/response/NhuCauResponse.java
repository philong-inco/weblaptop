package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.util.ConvertTime;
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
public class NhuCauResponse {
    Long id;
    String ma;
    String ten;
    String moTa;
    Integer trangThai;
    String ngayTao;
    String nguoiTao;
    String ngaySua;
    String nguoiSua;

    public void convertTime() {
        this.ngayTao = ConvertTime.convert(this.ngayTao);
        this.ngaySua = ConvertTime.convert(this.ngaySua);
    }
}

package com.dantn.weblaptop.dto.request.create_request;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FindRamFilter {

    String name;
    String ma;
    String dungLuong;
    String tocDoBus;
    String trangThai;
    String ngayTaoTruoc;
    String ngaySuaTruoc;
    String ngayTaoSau;
    String ngaySuaSau;
}

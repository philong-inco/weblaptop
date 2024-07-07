package com.dantn.weblaptop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChangeEmail_Dto {
    private Long id;

    private String ten;

    private String email;

    private String sdt;

    private String matKhau;
}

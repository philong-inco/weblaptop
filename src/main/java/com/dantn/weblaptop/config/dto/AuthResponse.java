package com.dantn.weblaptop.config.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String role;
    private Long id;
    private String ten;
    private String email;
    private String sdt;
    private String avatar;
    private Integer trangThai;
}

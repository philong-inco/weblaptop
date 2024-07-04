package com.dantn.weblaptop.dotgiamgia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DotGiamGiaDTO {
    private Long id;
    private String ma;
    private Integer loaiChietKhau;

}

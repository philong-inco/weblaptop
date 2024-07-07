package com.dantn.weblaptop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ForgotPassword_Dto {
    private String email;
    private String cccd;
}

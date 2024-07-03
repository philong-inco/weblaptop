package com.dantn.weblaptop.nhanvien.Dto.Request.Employee;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEmployee {
    @NotBlank(message = "Username không được để trống.")
    @Size(min = 6, max = 16, message = "username required 8-16 characters")
    String username;
    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 16, message = "Password required 8-16 characters")
    String password;
    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 16, message = "Password required 8-16 characters")
    String passwordComfirm;
    @NotBlank(message = "name không được để trống.")
    String name;
    @NotBlank(message = "email không được để trống.")
    String email;
    @NotBlank(message = "date of birth không được để trống.")
    LocalDateTime dateOfBirth;
    @NotBlank(message = "sex không được để trống.")
    Integer sex;
    @NotBlank(message = "Image không được để trống.")
    String image;
    @NotBlank(message = "CCCD không được để trống.")
    String cccd;
    @NotBlank(message = "NumberBanking không được để trống.")
    String numberBanking;
    @NotNull(message = "Status không được để trống.")
    @Min(value = 0, message = "Status is invalid")
    @Max(value = 1, message = "Status is invalid")
    Integer status;
    @NotNull(message = "Status không được để trống.")
    Integer idRole;
}

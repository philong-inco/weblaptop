package com.dantn.weblaptop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @JsonProperty("status_code")
    Integer statusCode;
    String error;
    Object message;
    T data;

    public ApiResponse(String s, Long count) {
    }

    public ApiResponse(boolean b, String s, Object o) {
    }

    public ApiResponse(String s, BigDecimal sum) {
    }
}
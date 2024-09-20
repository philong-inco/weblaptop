package com.dantn.weblaptop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseLong<T> {
    Integer code;
    String message;
    T data;
    String pageNo;
    String pageSize;
    String totalPage;
    String totalElement;



    public ResponseLong(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}

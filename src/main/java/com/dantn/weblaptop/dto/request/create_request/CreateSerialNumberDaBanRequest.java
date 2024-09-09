package com.dantn.weblaptop.dto.request.create_request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSerialNumberDaBanRequest {
    String billCode;
//    Long billId;
    Long productId;
    List<Long> listSerialNumberId;
}

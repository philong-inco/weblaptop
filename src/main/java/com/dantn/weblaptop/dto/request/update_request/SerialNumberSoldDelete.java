package com.dantn.weblaptop.dto.request.update_request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberSoldDelete {
    List<Long> serialNumberIds;
    String billCode;
}

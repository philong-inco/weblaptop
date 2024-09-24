package com.dantn.weblaptop.dto.request.update_request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberSoldDelete {
    List<Long> serialNumberIds;
    String billCode;
}

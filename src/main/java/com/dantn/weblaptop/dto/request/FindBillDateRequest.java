package com.dantn.weblaptop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindBillDateRequest {
    private Long startDate;
    private Long endDate;
}

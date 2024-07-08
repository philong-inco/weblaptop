package com.dantn.weblaptop.dto.request.update_request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdateHoaDonRequest {
    Long id;

    List<Long> listSP;

    Long idPGG;

    Long idKH;

    BigDecimal tongTien;
}

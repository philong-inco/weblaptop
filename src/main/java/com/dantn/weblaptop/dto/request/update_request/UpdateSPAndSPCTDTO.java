package com.dantn.weblaptop.dto.request.update_request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSPAndSPCTDTO {
    String tenSP;
    String moTa;
    String idThuongHieu;
    String idNhuCau;
    String idVGA;
    String idWebcam;
    String idManHinh;
    String idBanPhim;
    String idHeDieuHanh;
}

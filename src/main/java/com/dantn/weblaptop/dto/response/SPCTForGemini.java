package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SPCTForGemini {
    String tenSanPham;
    public static SPCTForGemini toResult(SanPhamChiTietClientDTO client){
        return client == null ? null : new SPCTForGemini(
                client.getTenSanPham() + " - " +
                        " Nhu cầu " + client.getNhuCau()  + " - " +
                        " Thương hiệu " + client.getThuongHieu() + " - " +
                        " Bàn phím " + client.getBanPhim() + " - " +
                        " CPU " + client.getCpu()  + " - " +
                        " Hệ điều hành " + client.getHeDieuHanh()  + " - " +
                        " Màn hình " + client.getManHinh() + " - " +
                        " Màu " + client.getMauSac() + " - " +
                        " Ổ cứng " + client.getOCung() + " - " +
                        " Ram " + client.getRam() + " - " +
                        " VGA " + client.getVga() + " - " +
                        " Webcam " + client.getWebcam()
        );
    }
}

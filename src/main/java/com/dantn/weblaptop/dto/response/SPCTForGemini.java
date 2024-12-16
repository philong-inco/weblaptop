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
                        " ID " + client.getSanPhamId()  + " - " +
                        " Nhu cầu " + client.getNhuCau()  + " - " +
                        " Thương hiệu " + client.getThuongHieu() + " - " +
                        " CPU " + client.getCpu()  + " - " +
                        " Màu " + client.getMauSac() + " - " +
                        " Ram " + client.getRam() + " - " +
                        " VGA " + client.getVga() + " - " +
                        " Giá bán " +  client.getGiaBan() + " - " +
                        " Giá sau khuyến mãi " + client.getGiaSauKhuyenMai()
        );
    }
}

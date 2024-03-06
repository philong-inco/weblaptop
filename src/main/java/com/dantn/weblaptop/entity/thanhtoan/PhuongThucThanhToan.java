package com.dantn.weblaptop.entity.thanhtoan;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "phuong_thuc_thanh_toan")
@Data
public class PhuongThucThanhToan extends BaseEntity {

    @Column(name = "ten_phuong_thuc")
    private String tenPhuongThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "phuongThucThanhToan",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDon> hoaDons;

    public void addHoaDon(HoaDon hoaDon) {
        if (hoaDons == null) {
            hoaDons = new HashSet<>();
        }
        hoaDons.add(hoaDon);
    }
}

package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "hinh_thuc_thanh_toan")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HinhThucThanhToan extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String ten;
    @Column(name = "mo_ta")
    Integer moTa;
    @OneToMany(mappedBy = "hinhThucThanhToan",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @ToString.Exclude
    Set<HoaDonHinhThucThanhToan> hoaDonHinhThucThanhToans;
}

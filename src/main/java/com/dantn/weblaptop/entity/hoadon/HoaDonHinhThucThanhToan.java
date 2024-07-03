package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "hoa_don_httc")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonHinhThucThanhToan extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "hinh_thuc_thanh_toan_id")
    HinhThucThanhToan hinhThucThanhToan;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "hoa_don_id")
    HoaDon hoaDon;
}

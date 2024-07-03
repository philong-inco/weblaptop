package com.dantn.weblaptop.entity.baohanh;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import java.math.BigDecimal;

@Entity
@Table(name = "san_pham_bao_hanh")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamBaoHanh extends BaseEntity {
    @Column(name = "gia_goi_bao_hanh")
    BigDecimal giaGoiBaoHanh;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_id")
    SanPham sanPham;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "bao_hanh_id")
    BaoHanh baoHanh;

}

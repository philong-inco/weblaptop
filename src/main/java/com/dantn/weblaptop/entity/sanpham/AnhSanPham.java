package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "anh_san_pham")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AnhSanPham extends BaseEntity {

    @Column(name = "trang_thai")
    Integer trangThai;
    String url;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_chi_tiet_id")
    @ToString.Exclude
    SanPhamChiTiet sanPhamChiTiet;
}

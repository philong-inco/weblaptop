package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.baohanh.SerialNumberBaoHanh;
import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @since 03/7/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "serial_number")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumber extends BaseEntity {
    @Column(name = "ma", unique = true, nullable = false)
    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ngay_nhap")
    LocalDateTime ngayNhap;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_chi_tiet_id")
    SanPhamChiTiet sanPhamChiTiet;
    @OneToMany(mappedBy = "serialNumber",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    Set<SerialNumberBaoHanh> serialNumberBaoHanhs;
    @OneToMany(mappedBy = "serialNumber",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    Set<SerialNumberDaBan> serialNumberDaBans;
}

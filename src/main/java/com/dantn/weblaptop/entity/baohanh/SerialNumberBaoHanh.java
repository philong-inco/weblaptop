package com.dantn.weblaptop.entity.baohanh;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "serial_number_bao_hanh")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberBaoHanh extends BaseEntity {
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ngay_gui")
    LocalDateTime ngayGui;
    @Column(name = "ngay_nhan")
    LocalDateTime ngayNhan;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "bao_hanh_id")
    BaoHanh baoHanh;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "serial_number_id")
    SerialNumber serialNumber;
}

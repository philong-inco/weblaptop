package com.dantn.weblaptop.entity.khachhang;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "thong_bao_khach_hang")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongBaoKhachHang extends BaseEntity {
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "noi_dung")
    String noiDung;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    KhachHang khachHang;
}

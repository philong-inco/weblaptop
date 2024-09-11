package com.dantn.weblaptop.entity.giohang;

import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@Table(name = "gio_hang_chi_tiet")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gio_hang", referencedColumnName = "id")
    GioHang gioHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serial_number", referencedColumnName = "id")
    SerialNumber serialNumber;

    @Column(name = "so_luong")
    Integer soLuong;

    @Column(name = "thanh_tien")
    BigDecimal thanhTen;
    //    0: vẫn còn : 1 hết sản phẩm :
    @Column(name = "trang_thai")
    Integer trangThai;
}

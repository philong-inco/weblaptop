package com.dantn.weblaptop.entity.giohang;

import com.dantn.weblaptop.entity.nguoidung.NguoiDung;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "gio_hang")
@Data
public class GioHangChiTiet {

    @Id
    @Setter(AccessLevel.NONE)
    private Long sanPhamChiTietId;

    @Id
    @Setter(AccessLevel.NONE)
    private Long nguoiDungId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sanPhamChiTietId", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoiDungId", referencedColumnName = "id", insertable = false, updatable = false)
    private NguoiDung nguoiDung;

    @Column(name = "so_luong")
    private Integer soLuong;

}

package com.dantn.weblaptop.entity.khuyenmai;

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
@Table(name = "khuyemai_sanphamchitiet")
@Data
public class KhuyenMaiSanPhamChiTiet {

    @Id
    @Setter(AccessLevel.NONE)
    private Long khuyenMaiId;

    @Id
    @Setter(AccessLevel.NONE)
    private Long sanPhamChiTietId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "khuyenMaiId", referencedColumnName = "id", insertable = false, updatable = false)
    private ChuongTrinhKhuyenMai khuyenMai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sanPhamChiTietId", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "ngay_ap_dung")
    private Long ngayApDung;

    @Column(name = "ngay_ket_thuc")
    private Long ngayKetThuc;

    @Column(name = "trang_thai")
    private Long trangThai;
}

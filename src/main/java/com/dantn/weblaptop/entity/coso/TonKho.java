package com.dantn.weblaptop.entity.coso;

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
@Table(name = "ton_kho")
@Data
public class TonKho {

    @Id
    @Setter(AccessLevel.NONE)
    private Long sanPhamChiTietId;

    @Id
    @Setter(AccessLevel.NONE)
    private Long coSoId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sanPhamChiTietId", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "coSoId", referencedColumnName = "id", insertable = false, updatable = false)
    private CoSo coSo;

    @Column(name = "so_luong")
    private Integer soLuong;
}

package com.dantn.weblaptop.entity.khuyenmai;

import com.dantn.weblaptop.entity.coso.CoSo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "khuyemmai_coso")
@Data
public class KhuyenMaiCoSo {

    @Id
    @Setter(AccessLevel.NONE)
    private Long khuyenMaiId;

    @Id
    @Setter(AccessLevel.NONE)
    private Long coSoId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "khuyenMaiId", referencedColumnName = "id", insertable = false, updatable = false)
    private ChuongTrinhKhuyenMai khuyenMai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "coSoId", referencedColumnName = "id", insertable = false, updatable = false)
    private CoSo coSo;

    @Column(name = "ngay_bat_dau")
    private Long ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Long ngayKetThuc;

    @Column(name = "trang_thai")
    private Long trangThai;
}

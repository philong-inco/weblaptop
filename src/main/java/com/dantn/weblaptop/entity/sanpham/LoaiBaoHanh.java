package com.dantn.weblaptop.entity.sanpham;


import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "loai_bao_hanh")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoaiBaoHanh extends BaseEntity {

    @Column(name = "ten_bao_hanh")
    private String tenBaoHanh;

    @Column(name = "mo_ta_bao_hanh")
    private String moTaBaoHanh;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "loaiBaoHanh",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<SanPhamChiTiet> sanPhamChiTiets;

    public void addSanPhamChiTiet(SanPhamChiTiet sanPhamChiTiet) {
        if (sanPhamChiTiets == null) {
            sanPhamChiTiets = new HashSet<>();
        }
        sanPhamChiTiets.add(sanPhamChiTiet);
    }
}

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
@Table(name = "cpu")
@Data
@EqualsAndHashCode(callSuper = false)
public class CPU extends BaseEntity {

    @Column(name = "ten_cpu")
    private String tenCPU;

    @Column(name = "mo_ta_cpu")
    private String moTaCPU;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "cpu",
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

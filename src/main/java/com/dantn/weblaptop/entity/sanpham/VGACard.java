package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vga_card")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VGACard extends BaseEntity {

    @Column(name = "ten_vga")
    private String tenVGA;

    @Column(name = "mo_ta_vga")
    private String moTaVGA;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( mappedBy = "vga",
                cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.EAGER)
    private Set<SanPhamChiTiet> sanPhamChiTiets;

    public void addSanPhamChiTiet(SanPhamChiTiet sanPhamChiTiet){
        if (sanPhamChiTiets == null){
            sanPhamChiTiets = new HashSet<>();
        }
        sanPhamChiTiets.add(sanPhamChiTiet);
    }

}

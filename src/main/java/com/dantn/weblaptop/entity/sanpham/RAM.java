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
@Table(name = "ram")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RAM extends BaseEntity {

    @Column(name = "ten_ram")
    private String tenRAM;

    @Column(name = "mo_ta_ram")
    private String moTaRAM;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( mappedBy = "ram",
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

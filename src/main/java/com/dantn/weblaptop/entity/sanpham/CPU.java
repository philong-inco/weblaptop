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
@Table(name = "cpu")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CPU extends BaseEntity {

    @Column(name = "ten_cpu")
    private String tenCPU;

    @Column(name = "mo_ta_cpu")
    private String moTaCPU;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( mappedBy = "cpu",
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

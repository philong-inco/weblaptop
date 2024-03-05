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
@Table(name = "man_hinh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManHinh extends BaseEntity {

    @Column(name = "ten_man_hinh")
    private String tenManHinh;

    @Column(name = "mo_ta_man_hinh")
    private String moTaManHinh;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( mappedBy = "manHinh",
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

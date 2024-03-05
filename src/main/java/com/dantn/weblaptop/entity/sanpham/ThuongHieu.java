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
@Table(name = "thuong_hieu")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThuongHieu extends BaseEntity {

    @Column(name = "ten_thuong_hieu")
    private String tenThuongHieu;

    @Column(name = "mo_ta_thuong_hieu")
    private String moTaThuongHieu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( mappedBy = "thuongHieu",
                cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.EAGER)
    private Set<SanPham> sanPhams;

    public void addSanPham(SanPham sanPham){
        if (sanPhams == null){
            sanPhams = new HashSet<>();
        }
        sanPhams.add(sanPham);
    }
}

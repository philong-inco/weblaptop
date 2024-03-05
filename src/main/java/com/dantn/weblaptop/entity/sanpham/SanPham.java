package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "san_pham")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SanPham extends BaseEntity {

    @Column(name = "ma_san_pham")
    private String maSanPham;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "mo_ta_san_pham")
    private String moTaSanPham;

    @Column(name = "_main_image")
    private String mainImage;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                            CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu thuongHieu;

}

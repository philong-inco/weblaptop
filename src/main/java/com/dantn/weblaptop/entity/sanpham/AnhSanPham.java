package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "anh_san_pham")
@Data
@EqualsAndHashCode(callSuper = false)
public class AnhSanPham extends BaseEntity {

    @Column(name = "path")
    private String path;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "san_pham_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SanPham sanPham;
}

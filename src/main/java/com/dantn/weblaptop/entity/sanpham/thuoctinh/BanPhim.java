package com.dantn.weblaptop.entity.sanpham.thuoctinh;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "ban_phim")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BanPhim extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String ten;
    @OneToMany(mappedBy = "banPhim",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @ToString.Exclude
    Set<SanPhamChiTiet> sanPhamChiTiets;
}

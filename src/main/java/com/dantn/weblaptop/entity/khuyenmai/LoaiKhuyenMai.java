package com.dantn.weblaptop.entity.khuyenmai;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "loai_khuyen_mai")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoaiKhuyenMai extends BaseEntity {

    @Column(name = "ten_loai_khuyen_mai")
    private String tenLoaiKhuyenMai;

    @Column(name = "mo_ta_loai_khuyen_mai")
    private String moTaLoaiKhuyenMai;

    @OneToMany(mappedBy = "loaiKhuyenMai",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ChuongTrinhKhuyenMai> chuongTrinhKhuyenMais;


    public void addThongTinNguoiDung(ChuongTrinhKhuyenMai khuyenMai) {
        if (chuongTrinhKhuyenMais == null) {
            chuongTrinhKhuyenMais = new HashSet<>();
        }
        chuongTrinhKhuyenMais.add(khuyenMai);
    }
}

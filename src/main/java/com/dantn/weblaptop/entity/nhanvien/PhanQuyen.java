package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.CoSo;
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
@Table(name = "phan_quyen")
@Data
@EqualsAndHashCode(callSuper = false)
public class PhanQuyen extends BaseEntity {

    @Column(name = "ten_quyen")
    private String tenQuyen;

    @Column(name = "mo_ta_quyen")
    private String moTaQuyen;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "phanQuyen",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<NhanVien> nhanViens;

    public void addCoSo(NhanVien nhanVien) {
        if (nhanViens == null) {
            nhanViens = new HashSet<>();
        }
        nhanViens.add(nhanVien);
    }
}

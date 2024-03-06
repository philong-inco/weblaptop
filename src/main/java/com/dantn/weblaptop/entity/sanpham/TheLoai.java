package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "the_loai")
@Data
@EqualsAndHashCode(callSuper = false)
public class TheLoai extends BaseEntity {

    @Column(name = "ma_the_loai")
    private String maTheLoai;

    @Column(name = "ten_the_loai")
    private String tenTheLoai;

    @Column(name = "mo_ta_the_loai")
    private String moTaTheLoai;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "the_loai_cha")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private TheLoai theLoaiCha;

    @OneToMany(mappedBy = "theLoaiCha",
            cascade = {
                    CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<TheLoai> theLoaiCons;


    public void addTheLoaiCon(TheLoai theLoaiCon) {
        if (theLoaiCons == null) {
            theLoaiCons = new HashSet<>();
        }
        theLoaiCons.add(theLoaiCon);
    }

}

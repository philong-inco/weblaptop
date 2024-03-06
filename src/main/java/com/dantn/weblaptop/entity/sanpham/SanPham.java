package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.nguoidung.NguoiDung;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "san_pham")
@Data
@EqualsAndHashCode(callSuper = false)
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thuong_hieu")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ThuongHieu thuongHieu;


    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "sanpham_theloai",
            joinColumns = @JoinColumn(name = "sanpham_id"),
            inverseJoinColumns = @JoinColumn(name = "theloai_id")
    )
    private Set<TheLoai> theLoais;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "danh_sach_yeu_thich",
            joinColumns = @JoinColumn(name = "sanpham_id"),
            inverseJoinColumns = @JoinColumn(name = "nguoidung_id"))
    private Set<NguoiDung> nguoiDungs;

    @OneToMany(mappedBy = "sanPham",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<AnhSanPham> anhSanPhams;

    public void addAnhSanPham(AnhSanPham anhSanPham) {
        if (anhSanPhams == null) {
            anhSanPhams = new HashSet<>();
        }
        anhSanPhams.add(anhSanPham);
    }

}

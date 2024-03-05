package com.dantn.weblaptop.entity.sanpham;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "san_pham_chi_tiet")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamChiTiet extends BaseEntity {

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_san_pham")
    private Float giaSanPham;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                CascadeType.MERGE, CascadeType.REFRESH},
                fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ram")
    private RAM ram;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cpu")
    private CPU cpu;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vga")
    private VGACard vga;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_man_hinh")
    private ManHinh manHinh;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loai_bao_hanh")
    private LoaiBaoHanh loaiBaoHanh;





}

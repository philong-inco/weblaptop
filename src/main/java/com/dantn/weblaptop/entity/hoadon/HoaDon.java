package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.coso.CoSo;
import com.dantn.weblaptop.entity.khuyenmai.ChuongTrinhKhuyenMai;
import com.dantn.weblaptop.entity.nguoidung.NguoiDung;
import com.dantn.weblaptop.entity.nguoidung.ThongTinNguoiDung;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.PhanQuyen;
import com.dantn.weblaptop.entity.thanhtoan.PhuongThucThanhToan;
import com.dantn.weblaptop.entity.voucher.Voucher;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @since 06/3/2024
 * Github: https://github.com/philong-inco
 */

@Entity
@Table(name = "hoa_don")
@Data
@EqualsAndHashCode(callSuper = false)
public class HoaDon extends BaseEntity {

    @Column(name = "ma_hoa_don")
    private String maHoaDon;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "tong_tien_hoa_don")
    private BigDecimal tongTienHoaDon;

    @Column(name = "giam_gia_voucher")
    private BigDecimal giamGiaVoucher;

    @Column(name = "giam_gia_khuyen_mai")
    private BigDecimal giamGiaKhuyenMai;

    @Column(name = "tong_tien_phai_tra")
    private BigDecimal tongTienPhaiTra;

    @Column(name = "tong_san_pham")
    private Integer tongSanPham;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "nhanvien_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NhanVien nhanVien;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "phuong_thuc_thanh_toan_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PhuongThucThanhToan phuongThucThanhToan;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "thong_tin_nguoi_dung_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ThongTinNguoiDung thongTinNguoiDung;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoi_dung_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NguoiDung nguoiDung;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "coso_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CoSo coSo;

    @OneToMany(mappedBy = "hoaDon",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HoaDonChiTiet> hoaDonChiTiets;

    @ManyToMany(mappedBy = "hoaDons",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<ChuongTrinhKhuyenMai> khuyenMais;

    @ManyToMany(mappedBy = "hoaDons", cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<Voucher> vouchers;

    public void addHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        if (hoaDonChiTiets == null) {
            hoaDonChiTiets = new HashSet<>();
        }
        hoaDonChiTiets.add(hoaDonChiTiet);
    }
}

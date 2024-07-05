package com.dantn.weblaptop.entity.hoadon;

import com.dantn.weblaptop.entity.base.BaseEntity;
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

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "phieu_giao_hang")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuGiaoHang extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ten_nguoi_nhan")
    String tenNguoiNhan;
    @Column(name = "sdt_nguoi_nhan")
    String sdtNguoiNhan;
    @Column(name = "dia_chi_nhan_hang")
    String diaChiNhanHang;
    @Column(name = "ghi_chu")
    String ghiChu;
    @Column(name = "cho_xem_hang")
    Boolean choXemHang;
    @Column(name = "tien_thu_ho")
    BigDecimal tienThuHo;
    @OneToMany(mappedBy = "phieuGiaoHang",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    Set<SerialNumberDaBan> serialNumberDaBans;
}

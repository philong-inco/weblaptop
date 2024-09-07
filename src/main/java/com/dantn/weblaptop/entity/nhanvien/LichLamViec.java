package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "lich_lam_viec")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichLamViec extends BaseEntity {
    @Column(name = "ma")
    String ma;
    @Column(name = "chuThich")
    String chuThich;
    @Column(name = "trang_thai")
    Integer trangThai;
    @Column(name = "ngay_lam_viec")
    LocalDate ngayLamViec;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_nhan_vien")
    NhanVien nhanVien;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ca_lam_viec")
    CaLamViec caLamViec;
}

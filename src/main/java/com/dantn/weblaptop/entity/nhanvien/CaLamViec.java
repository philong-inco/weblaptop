package com.dantn.weblaptop.entity.nhanvien;

import com.dantn.weblaptop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "ca_lam_viec")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaLamViec extends BaseEntity {
    String ma;
    @Column(name = "ca")
    Integer ca;
    @Column(name = "mo_ta")
    String moTa;
    @Column(name = "trang_thai")
    Integer trangThai;
    @OneToMany(mappedBy = "caLamViec",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    Set<LichLamViec> lichLamViecs;
}

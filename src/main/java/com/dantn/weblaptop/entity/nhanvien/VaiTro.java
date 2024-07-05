package com.dantn.weblaptop.entity.nhanvien;

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

import java.util.Set;
@Entity
@Table(name = "vai_tro")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaiTro extends BaseEntity {

    String ma;
    @Column(name = "trang_thai")
    Integer trangThai;
    String ten;
    @Column(name = "mo_ta")
    private String moTa;

    @OneToMany(mappedBy = "vaiTro",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<NhanVienVaiTro> nhanVienVaiTros;

}

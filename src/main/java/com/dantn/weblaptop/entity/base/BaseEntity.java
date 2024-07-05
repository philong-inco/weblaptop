package com.dantn.weblaptop.entity.base;

import com.dantn.weblaptop.util.AutoSetTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AutoSetTime.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false)
    Long id;

    @Column(name = "ngay_tao", updatable = false)
    Long ngayTao;

    @Column(name = "ngay_sua")
    Long ngaySua;

    @Column(name = "nguoi_tao")
    String nguoiTao;

    @Column(name = "nguoiSua")
    String nguoiSua;
}

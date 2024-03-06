package com.dantn.weblaptop.entity.voucher;

import com.dantn.weblaptop.entity.base.BaseEntity;
import com.dantn.weblaptop.entity.khuyenmai.ChuongTrinhKhuyenMai;
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
@Table(name = "loai_voucher")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoaiVoucher extends BaseEntity {

    @Column(name = "ten_loai_voucher")
    private String tenLoaiVoucher;

    @Column(name = "mo_ta_loai_voucher")
    private String moTaLoaiVoucher;

    @OneToMany(mappedBy = "loaiVoucher",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST,
                    CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Voucher> vouchers;

    public void addVoucher(Voucher voucher) {
        if (vouchers == null) {
            vouchers = new HashSet<>();
        }
        vouchers.add(voucher);
    }
}

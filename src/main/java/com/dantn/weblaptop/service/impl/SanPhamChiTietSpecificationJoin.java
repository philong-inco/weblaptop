package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.generics.GenericsSpecificationAttributeJoin;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SanPhamChiTietSpecificationJoin extends GenericsSpecificationAttributeJoin<SanPhamChiTiet> {

    public Specification<SanPhamChiTiet> hasNhuCau(String[] nhuCau) {
        return (root, query, builder) -> {
            if (nhuCau == null || nhuCau.length == 0)
                builder.conjunction();

            Join<SanPhamChiTiet, SanPham> sanPhamJoin = root.join("sanPham", JoinType.LEFT);
            Join<SanPham, NhuCau> nhuCauJoin = sanPhamJoin.join("nhuCau", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            for (String str : nhuCau) {
                predicates.add(builder.equal(nhuCauJoin.get("id"), Long.valueOf(str)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<SanPhamChiTiet> hasThuongHieu(String[] thuongHieu) {
        return (root, query, builder) -> {
            if (thuongHieu == null || thuongHieu.length == 0)
                builder.conjunction();

            Join<SanPhamChiTiet, SanPham> sanPhamJoin = root.join("sanPham", JoinType.LEFT);
            Join<SanPham, ThuongHieu> thuongHieuJoin = sanPhamJoin.join("thuongHieu", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            for (String str : thuongHieu) {
                predicates.add(builder.equal(thuongHieuJoin.get("id"), Long.valueOf(str)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }


}

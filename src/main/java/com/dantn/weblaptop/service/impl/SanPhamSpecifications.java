package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.NhuCau;
import com.dantn.weblaptop.entity.sanpham.SanPham;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;
import com.dantn.weblaptop.entity.sanpham.ThuongHieu;
import com.dantn.weblaptop.util.ConvertTime;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SanPhamSpecifications {

    public static Specification<SanPham> hasTen(String name) {
        return (root, query, builder) -> {
            if (name == null || name.isBlank())
                return builder.conjunction();
            return builder.like(root.get("ten"), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<SanPham> giaNhoHon(String gia) {
        return (root, query, builder) -> {
            if (gia == null || gia.isBlank())
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("san_pham_chi_tiet", JoinType.LEFT);

            return builder.greaterThanOrEqualTo(sanPhamChiTietJoin.get("gia_ban"), new BigDecimal(gia));
        };
    }

    public static Specification<SanPham> giaLonHon(String gia) {
        return (root, query, builder) -> {
            if (gia == null || gia.isBlank())
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("san_pham_chi_tiet", JoinType.LEFT);

            return builder.lessThanOrEqualTo(sanPhamChiTietJoin.get("gia_ban"), new BigDecimal(gia));
        };
    }

    public static Specification<SanPham> coDotGiamGia(String trangThai) {
        return (root, query, builder) -> {
            if (trangThai == null || trangThai.isBlank())
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("san_pham_chi_tiet", JoinType.LEFT);
            Join<SanPhamChiTiet, DotGiamGiaSanPhamChiTiet> giamGiaJoin = sanPhamChiTietJoin.join("dot_giam_gia_san_pham_chi_tiet", JoinType.INNER);

//            Predicate existPredicate = builder.exists(
//                    builder.createQuery()
//            );
//            return existPredicate;
            return null;
        };
    }

    public static Specification<SanPham> hasTrangThai(String trangThai) {
        return (root, query, builder) -> {
            if (trangThai == null || trangThai.isBlank())
                return builder.conjunction();
            return builder.equal(root.get("trangThai"), Integer.valueOf(trangThai));
        };
    }

    public static Specification<SanPham> hasMa(String ma) {
        return (root, query, builder) -> {
            if (ma == null || ma.isBlank())
                return builder.conjunction();
            return builder.equal(root.get("ma"), ma.toLowerCase());
        };
    }

    public static Specification<SanPham> ngayTaoTruoc(String date) {
        return (root, query, builder) -> {
            if (date == null || date.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(date);
            return builder.lessThanOrEqualTo(root.get("ngayTao"), dateLong);
        };
    }

    public static Specification<SanPham> ngayTaoSau(String date) {
        return (root, query, builder) -> {
            if (date == null || date.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(date);
            return builder.greaterThanOrEqualTo(root.get("ngayTao"), dateLong);
        };
    }

    public static Specification<SanPham> ngaySuaTruoc(String date) {
        return (root, query, builder) -> {
            if (date == null || date.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(date);
            return builder.lessThanOrEqualTo(root.get("ngaySua"), dateLong);
        };
    }

    public static Specification<SanPham> ngaySuaSau(String date) {
        return (root, query, builder) -> {
            if (date == null || date.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(date);
            return builder.greaterThanOrEqualTo(root.get("ngaySua"), dateLong);
        };
    }

    public static Specification<SanPham> hasThuongHieu(String[] thuongHieuName) {
        return (root, query, builder) -> {
            if (thuongHieuName == null || thuongHieuName.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            Join<SanPham, ThuongHieu> thuongHieuJoin = root.join("thuongHieu", JoinType.LEFT);
            for (String brand : thuongHieuName) {
                predicates.add(builder.like(builder.lower(thuongHieuJoin.get("ten")), "%" + brand.toLowerCase() + "%"));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SanPham> hasThuongHieuId(String[] thuongHieuId) {
        return (root, query, builder) -> {
            if (thuongHieuId == null || thuongHieuId.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            Join<SanPham, ThuongHieu> thuongHieuJoin = root.join("thuongHieu", JoinType.LEFT);
            for (String brand : thuongHieuId) {
                predicates.add(builder.equal(thuongHieuJoin.get("id"), Long.valueOf(brand)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SanPham> hasNhuCau(String[] nhuCauName) {
        return (root, query, builder) -> {
            if (nhuCauName == null || nhuCauName.length == 0)
                return builder.conjunction();
            Join<SanPham, NhuCau> nhuCauJoin = root.join("nhuCau", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String demand : nhuCauName) {
                predicates.add(builder.like(builder.lower(nhuCauJoin.get("ten")), "%" + demand.toLowerCase() + "%"));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SanPham> hasNhuCauId(String[] nhuCauId) {
        return (root, query, builder) -> {
            if (nhuCauId == null || nhuCauId.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            Join<SanPham, NhuCau> nhuCauJoin = root.join("nhuCau", JoinType.LEFT);
            for (String demand : nhuCauId) {
                predicates.add(builder.equal(nhuCauJoin.get("id"), Long.valueOf(demand)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SanPham> hasGiaNhoHon(String gia) {
        return (root, query, builder) -> {
            if (gia == null || gia.isBlank())
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);

            return builder.lessThanOrEqualTo(sanPhamChiTietJoin.get("giaBan"), gia);
        };
    }

    public static Specification<SanPham> hasGiaLonHon(String gia) {
        return (root, query, builder) -> {
            if (gia == null || gia.isBlank())
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);

            return builder.greaterThanOrEqualTo(sanPhamChiTietJoin.get("giaBan"), gia);
        };
    }

    public static <T> Specification<SanPham> hasNameAttribute(Class<T> attributeClass, String nameTable, String[] tens) {
        return (root, query, builder) -> {
            if (tens == null || tens.length == 0)
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);
            Join<SanPhamChiTiet, T> attributeTableJoin = sanPhamChiTietJoin.join(nameTable, JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            for (String ten : tens) {
                predicates.add(builder.like(builder.lower(attributeTableJoin.get("ten")), "%" + ten.toLowerCase() + "%"));
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static <T> Specification<SanPham> hasIdAttribute(Class<T> attributeClass, String nameTable, String[] ids) {
        return (root, query, builder) -> {
            if (ids == null || ids.length == 0)
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);
            Join<SanPhamChiTiet, T> attributeTableJoin = sanPhamChiTietJoin.join(nameTable, JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();
            for (String id : ids) {
                predicates.add(builder.equal(attributeTableJoin.get("id"), Long.valueOf(id)));
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }


}

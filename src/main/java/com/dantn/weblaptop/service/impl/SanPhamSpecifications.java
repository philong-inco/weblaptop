package com.dantn.weblaptop.service.impl;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
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

    public static Specification<SanPham> hasTen(String[] names) {
        return (root, query, builder) -> {
            if (names == null || names.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String name:names) {
                predicates.add(builder.like(builder.lower(root.get("ten")), "%" + name.toLowerCase() +"%"));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
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

    public static Specification<SanPham> hasTrangThai(String[] trangThais) {
        return (root, query, builder) -> {
            if (trangThais == null || trangThais.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String trangThai: trangThais) {
                predicates.add(builder.equal(root.get("trangThai"), Integer.valueOf(trangThai)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SanPham> hasMa(String[] mas) {
        return (root, query, builder) -> {
            if (mas == null || mas.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String ma:mas) {
                predicates.add(builder.equal(builder.lower(root.get("ma")), ma.toLowerCase()));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
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

    public static Specification<SanPham> hasKhuyenMai(String dangKhuyenMai) {
        return (root, query, builder) -> {
            if (!dangKhuyenMai.equals("1"))
                return builder.conjunction();
            Join<SanPham, SanPhamChiTiet> sanPhamChiTietJoin = root.join("sanPhamChiTiets", JoinType.LEFT);
            Join<SanPhamChiTiet, DotGiamGiaSanPhamChiTiet> dotGiamGiaSPCT = sanPhamChiTietJoin.join("dotGiamGiaSanPhamChiTiets", JoinType.LEFT);
            Join<DotGiamGiaSanPhamChiTiet, DotGiamGia> dotGiamGiaJoin = dotGiamGiaSPCT.join("dotGiamGia", JoinType.INNER);
            Predicate hasDiscountPredicate = builder.isNotNull(dotGiamGiaSPCT.get("id"));
            Predicate hasPromoActive = builder.equal(dotGiamGiaJoin.get("trangThai"),1);
            query.distinct(true);
            return builder.and(hasDiscountPredicate, hasPromoActive);
        };
    }


}

package com.dantn.weblaptop.generics;

import com.dantn.weblaptop.util.ConvertTime;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> là kiểu dữ liệu entity nhận về, <E> là entity cần join
 *            ví dụ SanPham, NhanVien, KhachHang, PhieuGiamGia, DotGiamGia, HoaDon
 *            create by philong-inco
 */
public class GenericsSpecificationAttributeJoin<T> {
    public <E> Specification<T> listStringLike(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                predicates.add(builder.like(builder.lower(joinTable.get(nameAttribute)), "%" + value.toLowerCase() + "%"));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <E> Specification<T> listStringEquals(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                predicates.add(builder.equal(builder.lower(joinTable.get(nameAttribute)), value.toLowerCase()));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <E> Specification<T> listIntegerEquals(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                predicates.add(builder.equal(joinTable.get(nameAttribute), Integer.valueOf(value)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <E> Specification<T> integerLessThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.lessThan(joinTable.get(nameAttribute), Integer.valueOf(value));
        };
    }

    public <E> Specification<T> integerLessThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.lessThanOrEqualTo(joinTable.get(nameAttribute), Integer.valueOf(value));
        };
    }

    public <E> Specification<T> integerGreaterThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.greaterThan(joinTable.get(nameAttribute), Integer.valueOf(value));
        };
    }

    public <E> Specification<T> integerGreaterThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.greaterThanOrEqualTo(joinTable.get(nameAttribute), Integer.valueOf(value));
        };
    }

    // Big decimal
    public <E> Specification<T> listBigDecimalEquals(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                predicates.add(builder.equal(joinTable.get(nameAttribute), new BigDecimal(value)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public <E> Specification<T> bigDecimalLessThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.lessThan(joinTable.get(nameAttribute), new BigDecimal(value));
        };
    }

    public <E> Specification<T> bigDecimalLessThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.lessThanOrEqualTo(joinTable.get(nameAttribute), new BigDecimal(value));
        };
    }

    public <E> Specification<T> bigDecimalGreaterThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.greaterThan(joinTable.get(nameAttribute), new BigDecimal(value));
        };
    }

    public <E> Specification<T> bigDecimalGreaterThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            return builder.greaterThanOrEqualTo(joinTable.get(nameAttribute), new BigDecimal(value));
        };
    }

    // LocalDateTime datetime
    public <E> Specification<T> listLocalDateTimeEquals(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(value);
                predicates.add(builder.equal(joinTable.get(nameAttribute), localDateTime));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    //
    public <E> Specification<T> localDateTimeLessThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(value);
            return builder.lessThan(joinTable.get(nameAttribute), localDateTime);
        };
    }

    public <E> Specification<T> localDateTimeLessThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(value);
            return builder.lessThanOrEqualTo(joinTable.get(nameAttribute), localDateTime);
        };
    }

    //
    public <E> Specification<T> localDateTimeGreaterThan(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(value);
            return builder.greaterThan(joinTable.get(nameAttribute), localDateTime);
        };
    }

    public <E> Specification<T> localDateTimeGreaterThanOrEquals(Class<E> classJoin, String nameTable, String nameAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(value);
            return builder.greaterThanOrEqualTo(joinTable.get(nameAttribute), localDateTime);
        };
    }

    // Long
    public <E> Specification<T> listLongEquals(Class<E> classJoin, String nameTable, String nameAttribute, String[] listValue) {
        return (root, query, builder) -> {
            if (listValue == null || listValue.length == 0)
                return builder.conjunction();
            Join<T, E> joinTable = root.join(nameTable, JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : listValue) {
                predicates.add(builder.equal(joinTable.get(nameAttribute), Long.valueOf(value)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }
}

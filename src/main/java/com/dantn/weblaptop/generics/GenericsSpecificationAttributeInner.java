package com.dantn.weblaptop.generics;

import com.dantn.weblaptop.util.ConvertTime;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> là kiểu dữ liệu entity nhận về
 *            ví dụ SanPham, NhanVien, KhachHang, PhieuGiamGia, DotGiamGia, HoaDon
 *            create by philong-inco
 */

public class GenericsSpecificationAttributeInner<T> {

    //  So sánh chứa keyword
    /**
     * @param columnAttribute - tên cột thuộc tính kiểu string
     * @param keyword         - mảng các giá trị string (vd: "laptop asus,laptop dell")
     * @return
     */
    public Specification<T> listStringLike(String columnAttribute, String[] keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String str : keyword) {
                predicates.add(builder.like(builder.lower(root.get(columnAttribute)), str.toLowerCase()));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu string
     * @param keyword         - mảng các giá trị string (vd: "MA1,MA2,MA3")
     * @return
     */
    public Specification<T> listStringEquals(String columnAttribute, String[] keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String str : keyword) {
                predicates.add(builder.equal(builder.lower(root.get(columnAttribute)), str.toLowerCase()));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Dùng để tìm theo điều kiện danh sách ID
     *
     * @param columnAttribute - tên cột thuộc tính kiểu long
     * @param value           - mảng các số ví dụ 1,2,3,4
     * @return
     */
    public Specification<T> listLongEquals(String columnAttribute, String[] value) {
        return (root, query, builder) -> {
            if (value == null || value.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String str : value) {
                predicates.add(builder.equal(root.get(columnAttribute), Long.valueOf(str)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    // Integer
    /**
     * @param columnAttribute - tên cột thuộc tính kiểu integer
     * @param value           - mảng các giá trị number (vd: 1,2,3,4)
     * @return
     */
    public Specification<T> listIntegerEquals(String columnAttribute, String[] value) {
        return (root, query, builder) -> {
            if (value == null || value.length == 0)
                return builder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            for (String str : value) {
                predicates.add(builder.equal(root.get(columnAttribute), Integer.valueOf(str)));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu integer
     * @param value           - giá trị một số nguyên (vd: 1)
     * @return
     */
    public Specification<T> integerLessThan(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.lessThan(root.get(columnAttribute), Integer.valueOf(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu integer
     * @param value           - giá trị một số nguyên (vd: 1)
     * @return
     */
    public Specification<T> integerLessThanOrEquals(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.lessThanOrEqualTo(root.get(columnAttribute), Integer.valueOf(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu integer
     * @param value           - giá trị một số nguyên (vd: 1)
     * @return
     */
    public Specification<T> integerGreaterThan(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.greaterThan(root.get(columnAttribute), Integer.valueOf(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu integer
     * @param value           - giá trị một số nguyên (vd: 1)
     * @return
     */
    public Specification<T> integerGreaterThanOrEquals(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.greaterThanOrEqualTo(root.get(columnAttribute), Integer.valueOf(value));
        };
    }


    // big decimal
    /**
     * @param columnAttribute - tên cột thuộc tính kiểu float/doulbe/big decimal
     * @param value           - giá trị một số nguyên (vd: 1.2)
     * @return
     */
    public Specification<T> bigDecimalEquals(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.equal(builder.lower(root.get(columnAttribute)), new BigDecimal(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu float/doulbe/big decimal
     * @param value           - giá trị một số nguyên (vd: 1.2)
     * @return
     */
    public Specification<T> bigDecimalLessThan(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.lessThan(root.get(columnAttribute), new BigDecimal(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu float/doulbe/big decimal
     * @param value           - giá trị một số nguyên (vd: 1.2)
     * @return
     */
    public Specification<T> bigDecimalLessThanOrEquals(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.lessThanOrEqualTo(root.get(columnAttribute), new BigDecimal(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu float/doulbe/big decimal
     * @param value           - giá trị một số nguyên (vd: 1.2)
     * @return
     */
    public Specification<T> bigDecimalGreaterThan(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.greaterThan(root.get(columnAttribute), new BigDecimal(value));
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu float/doulbe/big decimal
     * @param value           - giá trị một số nguyên (vd: 1.2)
     * @return
     */
    public Specification<T> bigDecimalGreaterThanOrEquals(String columnAttribute, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank())
                return builder.conjunction();
            return builder.greaterThanOrEqualTo(root.get(columnAttribute), new BigDecimal(value));
        };
    }

    // datetime

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu LocalDateTime
     * @param dateTime        - chuỗi có định dạng "dd-MM-yyyy" (vd: "12-02-2024")
     * @return
     */
    public Specification<T> localDateTimeBefore(String columnAttribute, String dateTime) {
        return (root, query, builder) -> {
            if (dateTime == null || dateTime.isBlank())
                return builder.conjunction();
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(dateTime);
            return builder.lessThanOrEqualTo(root.get(columnAttribute), localDateTime);
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu LocalDateTime
     * @param dateTime        - chuỗi có định dạng "dd-MM-yyyy" (vd: "12-02-2024")
     * @return
     */
    public Specification<T> localDateTimeAfter(String columnAttribute, String dateTime) {
        return (root, query, builder) -> {
            if (dateTime == null || dateTime.isBlank())
                return builder.conjunction();
            LocalDateTime localDateTime = ConvertTime.convertStringToLocalDateTime(dateTime);
            return builder.greaterThanOrEqualTo(root.get(columnAttribute), localDateTime);
        };
    }

    // long

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu Long
     * @param timestampLong   - chuỗi có định dạng timestamp (vd: "1716310800000")
     * @return
     */
    public Specification<T> timestampBefore(String columnAttribute, String timestampLong) {
        return (root, query, builder) -> {
            if (timestampLong == null || timestampLong.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(timestampLong);
            return builder.lessThanOrEqualTo(root.get(columnAttribute), dateLong);
        };
    }

    /**
     * @param columnAttribute - tên cột thuộc tính kiểu Long
     * @param timestampLong   - chuỗi có định dạng timestamp (vd: "1716310800000")
     * @return
     */
    public Specification<T> timestampAfter(String columnAttribute, String timestampLong) {
        return (root, query, builder) -> {
            if (timestampLong == null || timestampLong.isBlank())
                return builder.conjunction();
            long dateLong = ConvertTime.convertStringToLong(timestampLong);
            return builder.greaterThanOrEqualTo(root.get(columnAttribute), dateLong);
        };
    }
}

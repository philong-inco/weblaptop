package com.dantn.weblaptop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không được phân loại ngoại lệ !!!"),

    NOT_FOUND(1001, "Không tìm thấy"),
    PAYMENT_METHOD_NOT_FOUND(1002, "Phương thức thanh toán không tồn tại"),
    BILL_NOT_FOUND(1003, "Hóa đơn không tồn tại"),
    EMPLOYEE_NOT_FOUND(1004, "Nhân viên không tồn tại"),
    CUSTOMER_NOT_FOUND(1005, "Khách hàng không tồn tại"),
    MAXIMUM_5(1006, "Chỉ được tạo 5 hóa đơn chờ"),
    UNACHIEVABLE(1007, "Không thể thực hiện hành động"),
    // đầu 20 : date
    ERROR_DATE_1(2000, "Ngày kết thúc phải lớn hơn ngày bắt đầu"),
    ERROR_DATE_2(2001, "Ngày kết thúc phải lớn hơn hiện tại"),
    // đầu 30 : COUPONS
    COUPONS_NOT_FOUND(3000, "Phiếu giảm giá không tồn tại"),
    COUPONS_MAXIMUM_100(3001, "Giá trị phiếu giảm (%) phải <= 100"),
    //
    COUPONS_NAME_MIN_4(3002, "Tên phiếu phải từ 4 ký tự trở lên"),
    COUPONS_START_DATE_NOT_NULL(3003, "Ngày bắt đầu không được để trống"),
    COUPONS_END_DATE_NOT_NULL(3004, "Ngày kết thúc không được để trống"),
    COUPONS_DISCOUNT_TYPE_NOT_NULL(3005, "Loại giảm giá không được để trống"),
    COUPONS_DISCOUNT_VALUE_NOT_NULL(3006, "Giá trị giảm giá không được để trống"),
    COUPONS_MIN_DISCOUNT_VALUE(3007, "Giá trị giảm phải lớn hơn 0 VND"),
    COUPONS_MIN_ORDER_VALUE(3008, "Giá trị đơn tối thiểu phải lớn hơn 0 VND"),
    COUPONS_MAX_DISCOUNT(3009, "Giảm tối đa phải lớn hơn 0 VND"),
    COUPONS_APPLICABLE_SCOPE_NOT_NULL(3010, "Phạm vi áp dụng không được để trống"),
    COUPONS_MIN_QUANTITY(3011, "Số lượng phải lớn hơn 0"),
    COUPON_CODE_ALREADY_EXISTS(3012, "Mã phiếu đã tồn tại"),
    COUPON_DOES_NOT_APPLY(3013, "Không thể áp dụng phiếu giảm giá này"),

    // Đầu 4 Serial Number
    SERIAL_NUMBER_NOT_FOUND(4000, "Serial Number không tồn tại"),

    //
    PAY_NO_FOUND(5000, "Phương thức thanh toán không tồn tại"),
    BILL_NOT_STATUS(50001,"Hóa đơn chưa được xác nhân"),
    BILL_WITHOUT_PRODUCT (5002,"Hóa đơn chưa có sản phẩm")
    ;
    private Integer code;
    private String message;

}

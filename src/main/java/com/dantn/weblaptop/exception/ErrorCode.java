package com.dantn.weblaptop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    COUPON_CODE_NOT_BLANK(3014, "Hãy nhập mã phiếu giảm giá"),

    // Đầu 4 Serial Number
    SERIAL_NUMBER_NOT_FOUND(4000, "Serial Number không tồn tại"),

    //
    PAY_NO_FOUND(5000, "Phương thức thanh toán không tồn tại"),
    BILL_NOT_STATUS(50001, "Hóa đơn chưa được xác nhân"),
    BILL_WITHOUT_PRODUCT(5002, "Hóa đơn chưa có sản phẩm"),
    NOTE_NOT_BLANK(6000, "Ghi chú không được để trống"),
    NOTE_MIN_20(6001, "Ghi chú ít nhất 20 kí tự"),

    EMAIL_NOT_BLANK(6002, "Email không được để trống"),
    EMAIL_INVALID(6003, "Email không đúng định dạng"),
    PHONE_NOT_BLANK(6004, "Số điện thoại không được để trốnǵ"),
    PHONE_INVALID(6005, "Số điện thoại ko đúng định dạng"),
    NAME_NOT_BLANK(6006, "Tên không được để trống"),
    NAME_INVALID(6007, "Tên chỉ được là các chữ cái"),
    NAME_MIN_MAXIMUM(6011, "Tên từ 5 đến 25  ký tự"),
    PROVINCIAL_CITY_NOT_BLANK(6008, "Vui lòng chọn Tỉnh Thành"),
    DISTRICT_NOT_BLANK(6009, "Vui lòng chọn Quận Huyện"),
    WARD_NOT_BLANK(6010, "Vui lòng chọn Phường Xã"),
    COUPON_ALREADY_EXISTS_IN_BILL(6010, "Phiếu giảm giá đã có trong hóa đơn"),

    PRODUCT_DETAIL_NOT_FOUND(7000, "Sản phẩm chia tiết không tồn tại"),
    AMOUNT_INVALID(7001, "Số tiền không hợp lệ lớn hơn 0"),
    AMOUNT_NOT_NULL(7002, "Hãy nhập số tiền"),
    PAYMENT_HISTORY_NOT_FOUND(7003, "Không tìm thấy lịch sử thanh toán"),
    TIEN_KO_DU(7004, "Tiền nhập chưa đủ"),
    HOA_DON_HUY(7005,"Hóa đơn đã bị hủy không thể thêm sản phm"),
    HOA_DON_DANG_GIAO(7006,"Hóa đơn đang giao không thể thêm sản phẩm"),
    HOA_DON_DANG_THANH_CONG(7007,"Hóa đơn thành công không thể thêm sản phẩm"),
    THANH_TOAN_PHAI_TOAN_PHAM(7008, "Phải thanh toán sau cả hóa đơn"),

    CUSTOMER_NOT_FOUNT(8000, "Khach hàng không tồn tại"),
    PRODUCT_QUANTITY_IS_NOT_ENOUGH(8001, "Số lượng sản phẩm tại của hàng không đủ"),
    MINIMUM_NUMBER_OF_PRODUCTS_IS_1(8002, "Số lượng sản phẩm nhỏ nhất là 1"),
    PRICE_NOT_NULL(8003, "Giá sản phẩm chưa có"),
    PRODUCT_DETAIL_IS_NULL(8004, "Id sản phẩm chi tiết chưa có");


    private Integer code;
    private String message;

}

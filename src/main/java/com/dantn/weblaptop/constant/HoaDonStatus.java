package com.dantn.weblaptop.constant;

public enum HoaDonStatus {
    DON_MOI("Đơn mới"),
    CHO_THANH_TOAN("Chờ thanh toán"),
    CHO_XAC_NHAN("Chờ xác nhận"),
    CHO_GIAO("Chờ giao"),
    DANG_GIAO("Đang giao"),
    TRA_HANG_HOAN_TIEN("Trả hàng hoàn tiền"),
    HOAN_THANH("Hoàn thành"),
    HUY("Hủy"),
    XOA("Xóa"),
    XAC_NHAN("Xác nhận")
    ;

    HoaDonStatus(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return this.name;
    }

    public static HoaDonStatus getHoaDonStatusEnum(String name) {
        for (HoaDonStatus status : HoaDonStatus.values()) {
            if (name.equalsIgnoreCase(status.name))
                return status;
        }
        throw new IllegalArgumentException("Không tìm thấy trạng thái hóa đơn với " + name);
    }

    public static HoaDonStatus getHoaDonStatusEnumByKey(String key) {
        for (HoaDonStatus status : HoaDonStatus.values()) {
            if (key.equalsIgnoreCase(status.name())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy trạng thái hóa đơn với key: " + key);
    }
}

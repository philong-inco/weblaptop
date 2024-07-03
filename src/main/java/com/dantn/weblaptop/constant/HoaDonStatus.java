package com.dantn.weblaptop.constant;

import lombok.Getter;
import lombok.Setter;


public enum HoaDonStatus {
    CHO_THANH_TOAN("Chờ thanh toán"),
    CHO_XAC_NHAN("Chờ xác nhận"),
    CHO_GIAO("Chờ giao"),
    DANG_GIAO("Đang giao"),
    TRA_HANG_HOAN_TIEN("Trả hàng hoàn tiền"),
    HOAN_THANH("Hoàn thành")
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
}

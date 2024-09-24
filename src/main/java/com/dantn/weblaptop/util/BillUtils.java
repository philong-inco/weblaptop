package com.dantn.weblaptop.util;

import com.dantn.weblaptop.constant.HoaDonStatus;

public class BillUtils {

    public static Integer convertBillStatusEnumToInteger(HoaDonStatus status) {
        switch (status) {
            case DON_MOI:
                return 0;
            case CHO_THANH_TOAN:
                return 1;
            case CHO_XAC_NHAN:
                return 2;
            case CHO_GIAO:
                return 3;
            case DANG_GIAO:
                return 4;
            case TRA_HANG_HOAN_TIEN:
                return 5;
            case HOAN_THANH:
                return 6;
            case HUY:
                return 7;
            case XAC_NHAN:
                return 9;
            default:
                return -1;
        }
    }
}

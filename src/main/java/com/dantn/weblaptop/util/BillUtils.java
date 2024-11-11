package com.dantn.weblaptop.util;

import com.dantn.weblaptop.constant.HoaDonStatus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
            case XOA:
                return 8;
            case XAC_NHAN:
                return 9;
            default:
                return -1;
        }
    }

    public  static String convertMoney (BigDecimal money) {
        if(money.compareTo(BigDecimal.ZERO) == 0 || money== null) {
            return "0 đ";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.###", symbols);
        return formatter.format(money) + " đ";    }
}

package com.dantn.weblaptop.util;

import com.dantn.weblaptop.constant.HoaDonStatus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class BillUtils {

    public static Integer convertBillStatusEnumToInteger(HoaDonStatus status) {
        System.out.println("Đựược gọi -1");
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
            case HEN_LAI:
                return 13;
            default:
                return -1;
        }
    }

    public static String convertMoney(BigDecimal money) {
        if (money == null || money.compareTo(BigDecimal.ZERO) == 0) {
            return "0 đ";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.###", symbols);
        return formatter.format(money) + " đ";
    }

    public static Map<String, BigDecimal> listMoneyShip = new HashMap<String, BigDecimal>();


    public static BigDecimal getMoney(String billCode) {
        return listMoneyShip.getOrDefault(billCode, BigDecimal.ZERO);
    }

    public static boolean addMoney(String billCode, BigDecimal amount) {
        if (listMoneyShip.containsKey(billCode)) {
            System.out.println("Không thể thêm: Mã hóa đơn '" + billCode + "' đã tồn tại.");
            return false;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Không thể thêm: Số tiền phải lớn hơn 0.");
            return false;
        }

        listMoneyShip.put(billCode, amount);
        System.out.println("Thêm thành công: Mã hóa đơn '" + billCode + "', Số tiền: " + amount);
        return true;
    }


    public static boolean removeMoney(String billCode) {
        if (listMoneyShip.containsKey(billCode)) {
            listMoneyShip.remove(billCode);
            return true;
        }
        return false;
    }
    public static void printAll() {
        for (Map.Entry<String, BigDecimal> entry : listMoneyShip.entrySet()) {
            System.out.println("Bill Code: " + entry.getKey() + ", Amount: " + entry.getValue());
        }
    }
}
